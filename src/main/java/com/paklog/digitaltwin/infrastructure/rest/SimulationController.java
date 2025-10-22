package com.paklog.digitaltwin.infrastructure.rest;

import com.paklog.digitaltwin.application.command.CreateScenarioCommand;
import com.paklog.digitaltwin.application.command.CreateWarehouseModelCommand;
import com.paklog.digitaltwin.application.command.StartSimulationCommand;
import com.paklog.digitaltwin.application.service.SimulationApplicationService;
import com.paklog.digitaltwin.domain.aggregate.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
@Tag(name = "Digital Twin Simulation", description = "Warehouse simulation and digital twin APIs")
public class SimulationController {

    private final SimulationApplicationService applicationService;

    @PostMapping("/models")
    @Operation(summary = "Create warehouse model")
    public ResponseEntity<WarehouseModel> createModel(@RequestBody CreateWarehouseModelCommand command) {
        WarehouseModel model = applicationService.createWarehouseModel(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PostMapping("/scenarios")
    @Operation(summary = "Create simulation scenario")
    public ResponseEntity<SimulationScenario> createScenario(@RequestBody CreateScenarioCommand command) {
        SimulationScenario scenario = applicationService.createScenario(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(scenario);
    }

    @PostMapping("/runs")
    @Operation(summary = "Start simulation run")
    public ResponseEntity<SimulationRun> startSimulation(@RequestBody StartSimulationCommand command) {
        SimulationRun run = applicationService.startSimulation(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(run);
    }

    @GetMapping("/runs/{runId}")
    @Operation(summary = "Get simulation run details")
    public ResponseEntity<SimulationRun> getSimulationRun(@PathVariable String runId) {
        SimulationRun run = applicationService.getSimulationRun(runId);
        return ResponseEntity.ok(run);
    }

    @GetMapping("/scenarios")
    @Operation(summary = "List simulation scenarios")
    public ResponseEntity<List<SimulationScenario>> listScenarios(
            @RequestParam(required = false) Boolean activeOnly) {
        List<SimulationScenario> scenarios = applicationService.listScenarios(activeOnly);
        return ResponseEntity.ok(scenarios);
    }

    @GetMapping("/runs/{runId}/metrics")
    @Operation(summary = "Get simulation metrics")
    public ResponseEntity<List<PerformanceMetric>> getMetrics(@PathVariable String runId) {
        List<PerformanceMetric> metrics = applicationService.getMetrics(runId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/runs/{runId}/recommendations")
    @Operation(summary = "Get optimization recommendations")
    public ResponseEntity<List<OptimizationRecommendation>> getRecommendations(@PathVariable String runId) {
        List<OptimizationRecommendation> recommendations = applicationService.getRecommendations(runId);
        return ResponseEntity.ok(recommendations);
    }
}
