package com.paklog.digitaltwin.domain.service;

import com.paklog.digitaltwin.domain.aggregate.SimulationRun;
import com.paklog.digitaltwin.domain.aggregate.WarehouseModel;
import com.paklog.digitaltwin.domain.entity.SimulationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * Core simulation engine using discrete event simulation approach
 */
@Service
@Slf4j
public class SimulationEngine {
    
    private final Random random = new Random();

    public void runSimulation(SimulationRun run, WarehouseModel model) {
        log.info("Starting simulation run: {}", run.getRunId());
        
        run.start();
        
        // Simulate warehouse operations
        int totalEvents = 1000; // Simplified
        for (int i = 0; i < totalEvents && run.getState().name().equals("RUNNING"); i++) {
            // Generate simulation event
            SimulationEvent event = generateEvent(model, i);
            run.addEvent(event);
            
            // Update metrics
            updateMetrics(run, event);
            
            // Update progress
            run.setProgress((i + 1.0) / totalEvents * 100.0);
            
            // Simulate time passing
            try {
                Thread.sleep(10); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                run.fail("Simulation interrupted");
                return;
            }
        }
        
        run.complete();
        log.info("Simulation completed: {}", run.getRunId());
    }

    private SimulationEvent generateEvent(WarehouseModel model, int sequence) {
        String[] eventTypes = {"ORDER_RECEIVED", "PICK_STARTED", "PICK_COMPLETED", 
                               "PACK_STARTED", "PACK_COMPLETED", "SHIP_DISPATCHED"};
        
        return SimulationEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventTypes[random.nextInt(eventTypes.length)])
                .simulationTime(Instant.now())
                .wallClockTime(Instant.now())
                .resourceId("RESOURCE-" + random.nextInt(10))
                .description("Simulated event " + sequence)
                .build();
    }

    private void updateMetrics(SimulationRun run, SimulationEvent event) {
        // Calculate throughput
        double throughput = (double) run.getTotalEventsProcessed() / 
                          (run.getDurationSeconds() + 1) * 3600; // per hour
        run.updateMetric("throughput", throughput);
        
        // Calculate utilization
        double utilization = 0.65 + random.nextDouble() * 0.3; // 65-95%
        run.updateMetric("utilization", utilization);
        
        // Calculate cycle time
        double cycleTime = 120 + random.nextDouble() * 60; // 120-180 seconds
        run.updateMetric("cycle_time", cycleTime);
    }

    public void pauseSimulation(SimulationRun run) {
        run.pause();
        log.info("Simulation paused: {}", run.getRunId());
    }

    public void resumeSimulation(SimulationRun run) {
        run.resume();
        log.info("Simulation resumed: {}", run.getRunId());
    }
}
