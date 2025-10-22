package com.paklog.digitaltwin.domain.service;

import com.paklog.digitaltwin.domain.aggregate.SimulationScenario;
import com.paklog.digitaltwin.domain.aggregate.WarehouseModel;
import com.paklog.digitaltwin.domain.valueobject.OptimizationGoal;
import com.paklog.digitaltwin.domain.valueobject.ScenarioType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ScenarioBuilder {

    public SimulationScenario buildWhatIfScenario(WarehouseModel model, String name, 
                                                  String parameterToChange, Object newValue) {
        SimulationScenario scenario = SimulationScenario.builder()
                .scenarioId(UUID.randomUUID().toString())
                .name(name)
                .description("What-if analysis: " + parameterToChange)
                .type(ScenarioType.WHAT_IF)
                .baseModelId(model.getModelId())
                .optimizationGoal(OptimizationGoal.BALANCE_ALL)
                .createdAt(Instant.now())
                .active(true)
                .build();
        
        scenario.addParameter(parameterToChange, newValue);
        return scenario;
    }

    public SimulationScenario buildCapacityPlanningScenario(WarehouseModel model, 
                                                           int additionalCapacity) {
        SimulationScenario scenario = SimulationScenario.builder()
                .scenarioId(UUID.randomUUID().toString())
                .name("Capacity Planning - Add " + additionalCapacity + " units")
                .description("Evaluate impact of capacity expansion")
                .type(ScenarioType.CAPACITY_PLANNING)
                .baseModelId(model.getModelId())
                .optimizationGoal(OptimizationGoal.MAXIMIZE_THROUGHPUT)
                .createdAt(Instant.now())
                .active(true)
                .build();
        
        scenario.addParameter("additional_capacity", additionalCapacity);
        return scenario;
    }

    public SimulationScenario buildOptimizationScenario(WarehouseModel model, 
                                                       OptimizationGoal goal) {
        return SimulationScenario.builder()
                .scenarioId(UUID.randomUUID().toString())
                .name("Optimization - " + goal)
                .description("Find optimal configuration for " + goal)
                .type(ScenarioType.OPTIMIZATION)
                .baseModelId(model.getModelId())
                .optimizationGoal(goal)
                .createdAt(Instant.now())
                .active(true)
                .build();
    }
}
