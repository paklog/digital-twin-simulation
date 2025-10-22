package com.paklog.digitaltwin.domain.aggregate;

import com.paklog.digitaltwin.domain.valueobject.MetricType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "performance_metrics")
public class PerformanceMetric {
    @Id
    private String metricId;
    private String runId;
    private MetricType type;
    private String name;
    private String unit;
    private double currentValue;
    private double minValue;
    private double maxValue;
    private double avgValue;
    
    @Builder.Default
    private List<DataPoint> timeSeries = new ArrayList<>();
    
    private Instant firstRecordedAt;
    private Instant lastRecordedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataPoint {
        private Instant timestamp;
        private double value;
    }

    public void recordValue(double value, Instant timestamp) {
        DataPoint point = new DataPoint(timestamp, value);
        timeSeries.add(point);
        
        this.currentValue = value;
        this.lastRecordedAt = timestamp;
        
        if (firstRecordedAt == null) {
            firstRecordedAt = timestamp;
            minValue = value;
            maxValue = value;
            avgValue = value;
        } else {
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
            avgValue = (avgValue * (timeSeries.size() - 1) + value) / timeSeries.size();
        }
    }
}
