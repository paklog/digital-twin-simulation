package com.paklog.digitaltwin.application.query;

import com.paklog.digitaltwin.domain.valueobject.ScenarioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListScenariosQuery {
    private ScenarioType type;
    private Boolean activeOnly;
}
