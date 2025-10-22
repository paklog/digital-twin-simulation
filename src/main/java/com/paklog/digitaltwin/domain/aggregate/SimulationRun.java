package com.paklog.digitaltwin.domain.aggregate;

import com.paklog.digitaltwin.domain.entity.SimulationEvent;
import com.paklog.digitaltwin.domain.valueobject.ModelState;
import com.paklog.digitaltwin.domain.valueobject.SimulationTimeWindow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "simulation_runs")
public class SimulationRun {
    @Id
    private String runId;
    private String scenarioId;
    private String modelId;
    private String name;
    private ModelState state;
    private SimulationTimeWindow timeWindow;
    
    @Builder.Default
    private List<SimulationEvent> events = new ArrayList<>();
    
    @Builder.Default
    private Map<String, Double> metrics = new HashMap<>();
    
    private Instant startedAt;
    private Instant completedAt;
    private String initiatedBy;
    private String errorMessage;
    private int totalEventsProcessed;
    private double progress;

    public void start() {
        this.state = ModelState.RUNNING;
        this.startedAt = Instant.now();
        this.progress = 0.0;
    }

    public void pause() {
        this.state = ModelState.PAUSED;
    }

    public void resume() {
        this.state = ModelState.RUNNING;
    }

    public void complete() {
        this.state = ModelState.COMPLETED;
        this.completedAt = Instant.now();
        this.progress = 100.0;
    }

    public void fail(String errorMessage) {
        this.state = ModelState.FAILED;
        this.errorMessage = errorMessage;
        this.completedAt = Instant.now();
    }

    public void addEvent(SimulationEvent event) {
        this.events.add(event);
        this.totalEventsProcessed++;
    }

    public void updateMetric(String metricName, double value) {
        this.metrics.put(metricName, value);
    }

    public long getDurationSeconds() {
        if (startedAt == null) return 0;
        Instant end = completedAt != null ? completedAt : Instant.now();
        return java.time.Duration.between(startedAt, end).getSeconds();
    }
}
