package com.paklog.digitaltwin.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulationEvent {
    private String eventId;
    private String eventType;
    private Instant simulationTime;
    private Instant wallClockTime;
    private String resourceId;
    private String description;
    private Object metadata;
}
