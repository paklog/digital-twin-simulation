package com.paklog.digitaltwin.domain.repository;

import com.paklog.digitaltwin.domain.aggregate.SimulationRun;
import com.paklog.digitaltwin.domain.valueobject.ModelState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulationRunRepository extends MongoRepository<SimulationRun, String> {
    List<SimulationRun> findByScenarioId(String scenarioId);
    List<SimulationRun> findByState(ModelState state);
    long countByState(ModelState state);
}
