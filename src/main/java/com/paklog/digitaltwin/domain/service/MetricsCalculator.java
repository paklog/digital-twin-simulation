package com.paklog.digitaltwin.domain.service;

import com.paklog.digitaltwin.domain.aggregate.PerformanceMetric;
import com.paklog.digitaltwin.domain.aggregate.SimulationRun;
import com.paklog.digitaltwin.domain.valueobject.MetricType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MetricsCalculator {

    public List<PerformanceMetric> calculateMetrics(SimulationRun run) {
        List<PerformanceMetric> metrics = new ArrayList<>();
        
        // Throughput metric
        PerformanceMetric throughput = PerformanceMetric.builder()
                .metricId(UUID.randomUUID().toString())
                .runId(run.getRunId())
                .type(MetricType.THROUGHPUT)
                .name("Orders per Hour")
                .unit("orders/hour")
                .currentValue(run.getMetrics().getOrDefault("throughput", 0.0))
                .firstRecordedAt(run.getStartedAt())
                .lastRecordedAt(Instant.now())
                .build();
        metrics.add(throughput);
        
        // Utilization metric
        PerformanceMetric utilization = PerformanceMetric.builder()
                .metricId(UUID.randomUUID().toString())
                .runId(run.getRunId())
                .type(MetricType.UTILIZATION)
                .name("Resource Utilization")
                .unit("percentage")
                .currentValue(run.getMetrics().getOrDefault("utilization", 0.0))
                .firstRecordedAt(run.getStartedAt())
                .lastRecordedAt(Instant.now())
                .build();
        metrics.add(utilization);
        
        // Cycle time metric
        PerformanceMetric cycleTime = PerformanceMetric.builder()
                .metricId(UUID.randomUUID().toString())
                .runId(run.getRunId())
                .type(MetricType.CYCLE_TIME)
                .name("Average Cycle Time")
                .unit("seconds")
                .currentValue(run.getMetrics().getOrDefault("cycle_time", 0.0))
                .firstRecordedAt(run.getStartedAt())
                .lastRecordedAt(Instant.now())
                .build();
        metrics.add(cycleTime);
        
        return metrics;
    }

    public double calculateEfficiency(SimulationRun run) {
        double throughput = run.getMetrics().getOrDefault("throughput", 0.0);
        double utilization = run.getMetrics().getOrDefault("utilization", 0.0);
        double cycleTime = run.getMetrics().getOrDefault("cycle_time", 180.0);
        
        // Efficiency = (throughput * utilization) / cycleTime (normalized)
        return (throughput * utilization) / cycleTime * 100;
    }
}
