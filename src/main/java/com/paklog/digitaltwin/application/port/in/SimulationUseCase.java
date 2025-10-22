package com.paklog.digitaltwin.application.port.in;

import com.paklog.digitaltwin.application.command.StartSimulationCommand;
import com.paklog.digitaltwin.domain.aggregate.SimulationRun;

public interface SimulationUseCase {
    SimulationRun startSimulation(StartSimulationCommand command);
    void pauseSimulation(String runId);
    void resumeSimulation(String runId);
    void stopSimulation(String runId);
}
