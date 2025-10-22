package com.paklog.digitaltwin.application.service;

import com.paklog.digitaltwin.application.command.CreateScenarioCommand;
import com.paklog.digitaltwin.application.command.CreateWarehouseModelCommand;
import com.paklog.digitaltwin.application.command.StartSimulationCommand;
import com.paklog.digitaltwin.application.port.out.PublishEventPort;
import com.paklog.digitaltwin.domain.aggregate.*;
import com.paklog.digitaltwin.domain.event.SimulationCompletedEvent;
import com.paklog.digitaltwin.domain.event.SimulationStartedEvent;
import com.paklog.digitaltwin.domain.repository.*;
import com.paklog.digitaltwin.domain.service.MetricsCalculator;
import com.paklog.digitaltwin.domain.service.OptimizationService;
import com.paklog.digitaltwin.domain.service.SimulationEngine;
import com.paklog.digitaltwin.domain.valueobject.ModelState;
import com.paklog.digitaltwin.domain.valueobject.SimulationTimeWindow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationApplicationService {

    private final WarehouseModelRepository warehouseModelRepository;
    private final SimulationRunRepository simulationRunRepository;
    private final SimulationScenarioRepository scenarioRepository;
    private final PerformanceMetricRepository metricRepository;
    private final OptimizationRecommendationRepository recommendationRepository;
    
    private final SimulationEngine simulationEngine;
    private final MetricsCalculator metricsCalculator;
    private final OptimizationService optimizationService;
    private final PublishEventPort publishEventPort;

    @Transactional
    public WarehouseModel createWarehouseModel(CreateWarehouseModelCommand command) {
        WarehouseModel model = WarehouseModel.builder()
                .modelId(UUID.randomUUID().toString())
                .warehouseId(command.getWarehouseId())
                .name(command.getName())
                .description(command.getDescription())
                .dimensions(command.getDimensions())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .synchronized(false)
                .build();
        
        return warehouseModelRepository.save(model);
    }

    @Transactional
    public SimulationScenario createScenario(CreateScenarioCommand command) {
        SimulationScenario scenario = SimulationScenario.builder()
                .scenarioId(UUID.randomUUID().toString())
                .name(command.getName())
                .description(command.getDescription())
                .type(command.getType())
                .baseModelId(command.getBaseModelId())
                .parameters(command.getParameters())
                .optimizationGoal(command.getOptimizationGoal())
                .createdAt(Instant.now())
                .createdBy(command.getCreatedBy())
                .active(true)
                .build();
        
        return scenarioRepository.save(scenario);
    }

    @Transactional
    public SimulationRun startSimulation(StartSimulationCommand command) {
        // Validate concurrent simulations limit
        long runningSimulations = simulationRunRepository.countByState(ModelState.RUNNING);
        if (runningSimulations >= 100) {
            throw new IllegalStateException("Maximum concurrent simulations reached");
        }
        
        SimulationTimeWindow timeWindow = new SimulationTimeWindow(
                command.getStartTime(),
                command.getEndTime(),
                command.getAccelerationFactor()
        );
        
        SimulationRun run = SimulationRun.builder()
                .runId(UUID.randomUUID().toString())
                .scenarioId(command.getScenarioId())
                .modelId(command.getModelId())
                .name(command.getName())
                .state(ModelState.IDLE)
                .timeWindow(timeWindow)
                .initiatedBy(command.getInitiatedBy())
                .totalEventsProcessed(0)
                .progress(0.0)
                .build();
        
        run = simulationRunRepository.save(run);
        
        // Publish event
        SimulationStartedEvent event = new SimulationStartedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setOccurredAt(Instant.now());
        event.setAggregateId(run.getRunId());
        event.setRunId(run.getRunId());
        event.setScenarioId(command.getScenarioId());
        event.setModelId(command.getModelId());
        event.setAccelerationFactor(command.getAccelerationFactor());
        
        publishEventPort.publish(event);
        
        // Execute simulation asynchronously
        executeSimulationAsync(run.getRunId());
        
        return run;
    }

    @Async
    public void executeSimulationAsync(String runId) {
        SimulationRun run = simulationRunRepository.findById(runId)
                .orElseThrow(() -> new IllegalArgumentException("Run not found"));
        
        WarehouseModel model = warehouseModelRepository.findById(run.getModelId())
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));
        
        // Run simulation
        simulationEngine.runSimulation(run, model);
        
        // Save results
        run = simulationRunRepository.save(run);
        
        // Calculate and save metrics
        List<PerformanceMetric> metrics = metricsCalculator.calculateMetrics(run);
        metricRepository.saveAll(metrics);
        
        // Generate optimization recommendations
        List<OptimizationRecommendation> recommendations = 
                optimizationService.generateRecommendations(run);
        recommendationRepository.saveAll(recommendations);
        
        // Publish completion event
        SimulationCompletedEvent event = new SimulationCompletedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setOccurredAt(Instant.now());
        event.setAggregateId(run.getRunId());
        event.setRunId(run.getRunId());
        event.setTotalEventsProcessed(run.getTotalEventsProcessed());
        event.setFinalMetrics(run.getMetrics());
        event.setDurationSeconds(run.getDurationSeconds());
        
        publishEventPort.publish(event);
    }

    @Transactional(readOnly = true)
    public SimulationRun getSimulationRun(String runId) {
        return simulationRunRepository.findById(runId)
                .orElseThrow(() -> new IllegalArgumentException("Run not found: " + runId));
    }

    @Transactional(readOnly = true)
    public List<SimulationScenario> listScenarios(Boolean activeOnly) {
        if (activeOnly != null && activeOnly) {
            return scenarioRepository.findByActive(true);
        }
        return scenarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PerformanceMetric> getMetrics(String runId) {
        return metricRepository.findByRunId(runId);
    }

    @Transactional(readOnly = true)
    public List<OptimizationRecommendation> getRecommendations(String runId) {
        return recommendationRepository.findByRunId(runId);
    }
}
