package com.paklog.digitaltwin.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paklog.digitaltwin.application.port.out.PublishEventPort;
import com.paklog.digitaltwin.domain.event.DomainEvent;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher implements PublishEventPort {

    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String TOPIC = "digital-twin.events";

    @Override
    public void publish(DomainEvent event) {
        try {
            CloudEvent cloudEvent = CloudEventBuilder.v1()
                    .withId(event.getEventId())
                    .withType("com.paklog.digitaltwin." + event.getEventType())
                    .withSource(URI.create("digital-twin-simulation"))
                    .withData("application/json", objectMapper.writeValueAsBytes(event))
                    .build();
            
            kafkaTemplate.send(TOPIC, event.getAggregateId(), cloudEvent);
            log.info("Published event: {} for aggregate: {}", event.getEventType(), event.getAggregateId());
        } catch (Exception e) {
            log.error("Error publishing event", e);
        }
    }
}
