package com.paklog.digitaltwin.domain.repository;

import com.paklog.digitaltwin.domain.aggregate.WarehouseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseModelRepository extends MongoRepository<WarehouseModel, String> {
    Optional<WarehouseModel> findByWarehouseId(String warehouseId);
}
