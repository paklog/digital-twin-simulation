package com.paklog.digitaltwin.domain.aggregate;

import com.paklog.digitaltwin.domain.valueobject.OptimizationGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "optimization_recommendations")
public class OptimizationRecommendation {
    @Id
    private String recommendationId;
    private String runId;
    private String title;
    private String description;
    private OptimizationGoal goal;
    
    @Builder.Default
    private Map<String, Object> suggestedChanges = new HashMap<>();
    
    private double expectedImprovement;
    private double confidence;
    private String impactAnalysis;
    private double estimatedCost;
    private double estimatedROI;
    private Instant generatedAt;
    private boolean implemented;
    private Instant implementedAt;

    public void markImplemented() {
        this.implemented = true;
        this.implementedAt = Instant.now();
    }

    public void addChange(String parameter, Object value) {
        this.suggestedChanges.put(parameter, value);
    }
}
