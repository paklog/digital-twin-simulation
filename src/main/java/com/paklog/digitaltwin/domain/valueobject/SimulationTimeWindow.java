package com.paklog.digitaltwin.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulationTimeWindow {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int accelerationFactor; // 1x to 1000x

    public long getDurationMinutes() {
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }
}
