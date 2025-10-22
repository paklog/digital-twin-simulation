package com.paklog.digitaltwin.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartSimulationCommand {
    private String scenarioId;
    private String modelId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int accelerationFactor;
    private String initiatedBy;
}
