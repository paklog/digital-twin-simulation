package com.paklog.digitaltwin.application.command;

import com.paklog.digitaltwin.domain.valueobject.OptimizationGoal;
import com.paklog.digitaltwin.domain.valueobject.ScenarioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateScenarioCommand {
    private String name;
    private String description;
    private ScenarioType type;
    private String baseModelId;
    private Map<String, Object> parameters;
    private OptimizationGoal optimizationGoal;
    private String createdBy;
}
