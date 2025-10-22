package com.paklog.digitaltwin.domain.event;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimulationCompletedEvent extends DomainEvent {
    private String runId;
    private int totalEventsProcessed;
    private Map<String, Double> finalMetrics;
    private long durationSeconds;

    @Override
    public String getEventType() {
        return "SimulationCompleted";
    }
}
