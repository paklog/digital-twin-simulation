package com.paklog.digitaltwin.domain.service;

import com.paklog.digitaltwin.domain.aggregate.OptimizationRecommendation;
import com.paklog.digitaltwin.domain.aggregate.SimulationRun;
import com.paklog.digitaltwin.domain.valueobject.OptimizationGoal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OptimizationService {

    public List<OptimizationRecommendation> generateRecommendations(SimulationRun run) {
        List<OptimizationRecommendation> recommendations = new ArrayList<>();
        
        double throughput = run.getMetrics().getOrDefault("throughput", 0.0);
        double utilization = run.getMetrics().getOrDefault("utilization", 0.0);
        double cycleTime = run.getMetrics().getOrDefault("cycle_time", 0.0);
        
        // Recommendation 1: Add resources if utilization is high
        if (utilization > 0.85) {
            OptimizationRecommendation rec = OptimizationRecommendation.builder()
                    .recommendationId(UUID.randomUUID().toString())
                    .runId(run.getRunId())
                    .title("Add 2 Additional Picking Resources")
                    .description("Current utilization is " + String.format("%.1f%%", utilization * 100) + 
                               ". Adding resources will reduce bottlenecks.")
                    .goal(OptimizationGoal.MAXIMIZE_THROUGHPUT)
                    .expectedImprovement(0.15)
                    .confidence(0.87)
                    .impactAnalysis("Expected 15% throughput increase")
                    .estimatedCost(150000.0)
                    .estimatedROI(2.3)
                    .generatedAt(Instant.now())
                    .implemented(false)
                    .build();
            
            rec.addChange("additional_pickers", 2);
            recommendations.add(rec);
        }
        
        // Recommendation 2: Optimize layout if cycle time is high
        if (cycleTime > 150) {
            OptimizationRecommendation rec = OptimizationRecommendation.builder()
                    .recommendationId(UUID.randomUUID().toString())
                    .runId(run.getRunId())
                    .title("Optimize Zone Layout")
                    .description("Current cycle time is " + String.format("%.0f", cycleTime) + 
                               " seconds. Reorganizing hot zones can reduce travel time.")
                    .goal(OptimizationGoal.MINIMIZE_CYCLE_TIME)
                    .expectedImprovement(0.20)
                    .confidence(0.82)
                    .impactAnalysis("Expected 20% cycle time reduction")
                    .estimatedCost(50000.0)
                    .estimatedROI(3.5)
                    .generatedAt(Instant.now())
                    .implemented(false)
                    .build();
            
            rec.addChange("layout_optimization", "ABC_ANALYSIS");
            recommendations.add(rec);
        }
        
        log.info("Generated {} recommendations for run {}", recommendations.size(), run.getRunId());
        return recommendations;
    }

    public double evaluateScenario(SimulationRun baselineRun, SimulationRun scenarioRun) {
        double baselineScore = calculateScore(baselineRun);
        double scenarioScore = calculateScore(scenarioRun);
        
        return (scenarioScore - baselineScore) / baselineScore;
    }

    private double calculateScore(SimulationRun run) {
        double throughput = run.getMetrics().getOrDefault("throughput", 0.0);
        double utilization = run.getMetrics().getOrDefault("utilization", 0.0);
        double cycleTime = run.getMetrics().getOrDefault("cycle_time", 180.0);
        
        // Weighted score
        return (throughput * 0.4) + (utilization * 100 * 0.3) + ((300 - cycleTime) * 0.3);
    }
}
