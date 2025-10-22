package com.paklog.digitaltwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableKafka
@EnableAsync
@EnableScheduling
public class DigitalTwinSimulationApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigitalTwinSimulationApplication.java, args);
    }
}
