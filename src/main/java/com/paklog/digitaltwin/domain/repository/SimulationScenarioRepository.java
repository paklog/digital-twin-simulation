package com.paklog.digitaltwin.domain.repository;

import com.paklog.digitaltwin.domain.aggregate.SimulationScenario;
import com.paklog.digitaltwin.domain.valueobject.ScenarioType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulationScenarioRepository extends MongoRepository<SimulationScenario, String> {
    List<SimulationScenario> findByType(ScenarioType type);
    List<SimulationScenario> findByActive(boolean active);
}
