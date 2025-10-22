package com.paklog.digitaltwin.domain.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModelUpdatedEvent extends DomainEvent {
    private String modelId;
    private String updateType;
    private String description;

    @Override
    public String getEventType() {
        return "ModelUpdated";
    }
}
