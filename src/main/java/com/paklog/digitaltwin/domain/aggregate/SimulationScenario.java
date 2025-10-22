package com.paklog.digitaltwin.domain.aggregate;

import com.paklog.digitaltwin.domain.valueobject.OptimizationGoal;
import com.paklog.digitaltwin.domain.valueobject.ScenarioType;
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
@Document(collection = "simulation_scenarios")
public class SimulationScenario {
    @Id
    private String scenarioId;
    private String name;
    private String description;
    private ScenarioType type;
    private String baseModelId;
    
    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();
    
    private OptimizationGoal optimizationGoal;
    private Instant createdAt;
    private String createdBy;
    private boolean active;

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Object getParameter(String key) {
        return this.parameters.get(key);
    }
}
