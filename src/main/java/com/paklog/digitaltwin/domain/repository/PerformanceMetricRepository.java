package com.paklog.digitaltwin.domain.repository;

import com.paklog.digitaltwin.domain.aggregate.PerformanceMetric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceMetricRepository extends MongoRepository<PerformanceMetric, String> {
    List<PerformanceMetric> findByRunId(String runId);
}
