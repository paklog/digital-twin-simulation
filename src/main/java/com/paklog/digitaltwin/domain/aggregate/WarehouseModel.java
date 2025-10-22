package com.paklog.digitaltwin.domain.aggregate;

import com.paklog.digitaltwin.domain.entity.WarehouseZone;
import com.paklog.digitaltwin.domain.valueobject.Coordinates3D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "warehouse_models")
public class WarehouseModel {
    @Id
    private String modelId;
    private String warehouseId;
    private String name;
    private String description;
    private Coordinates3D dimensions;
    
    @Builder.Default
    private List<WarehouseZone> zones = new ArrayList<>();
    
    private int totalAisles;
    private int totalLocations;
    private int totalResources;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean synchronized;
    private Instant lastSyncTime;

    public void addZone(WarehouseZone zone) {
        this.zones.add(zone);
    }

    public double getTotalVolume() {
        return dimensions.getX() * dimensions.getY() * dimensions.getZ();
    }

    public double getAverageZoneUtilization() {
        return zones.stream()
                .mapToDouble(WarehouseZone::getUtilization)
                .average()
                .orElse(0.0);
    }
}
