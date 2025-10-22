package com.paklog.digitaltwin.domain.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimulationStartedEvent extends DomainEvent {
    private String runId;
    private String scenarioId;
    private String modelId;
    private int accelerationFactor;

    @Override
    public String getEventType() {
        return "SimulationStarted";
    }
}
