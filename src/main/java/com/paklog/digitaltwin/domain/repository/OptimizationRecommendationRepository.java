package com.paklog.digitaltwin.domain.repository;

import com.paklog.digitaltwin.domain.aggregate.OptimizationRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptimizationRecommendationRepository extends MongoRepository<OptimizationRecommendation, String> {
    List<OptimizationRecommendation> findByRunId(String runId);
    List<OptimizationRecommendation> findByImplemented(boolean implemented);
}
