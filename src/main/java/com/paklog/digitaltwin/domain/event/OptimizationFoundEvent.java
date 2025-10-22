package com.paklog.digitaltwin.domain.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OptimizationFoundEvent extends DomainEvent {
    private String recommendationId;
    private String runId;
    private double expectedImprovement;
    private double confidence;

    @Override
    public String getEventType() {
        return "OptimizationFound";
    }
}
