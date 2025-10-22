package com.paklog.digitaltwin.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent {
    private String eventId;
    private Instant occurredAt;
    private String aggregateId;

    public abstract String getEventType();
}
