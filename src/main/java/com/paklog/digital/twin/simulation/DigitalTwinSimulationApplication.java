package com.paklog.digital.twin.simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Digital Twin Simulation
 *
 * Virtual warehouse modeling and what-if analysis
 *
 * @author Paklog Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableKafka
@EnableMongoAuditing
public class DigitalTwinSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalTwinSimulationApplication.class, args);
    }
}