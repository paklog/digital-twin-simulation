package com.paklog.digitaltwin.application.port.out;

import com.paklog.digitaltwin.domain.event.DomainEvent;

public interface PublishEventPort {
    void publish(DomainEvent event);
}
