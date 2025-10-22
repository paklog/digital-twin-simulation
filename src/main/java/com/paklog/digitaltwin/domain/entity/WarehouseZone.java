package com.paklog.digitaltwin.domain.entity;

import com.paklog.digitaltwin.domain.valueobject.Coordinates3D;
import com.paklog.digitaltwin.domain.valueobject.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseZone {
    private String zoneId;
    private String name;
    private ZoneType type;
    private Coordinates3D startCoordinates;
    private Coordinates3D endCoordinates;
    private int capacity;
    private int currentOccupancy;
    
    @Builder.Default
    private List<String> aisleIds = new ArrayList<>();
    
    @Builder.Default
    private List<String> resourceIds = new ArrayList<>();

    public double getUtilization() {
        return capacity > 0 ? (double) currentOccupancy / capacity : 0.0;
    }

    public double getVolume() {
        double dx = Math.abs(endCoordinates.getX() - startCoordinates.getX());
        double dy = Math.abs(endCoordinates.getY() - startCoordinates.getY());
        double dz = Math.abs(endCoordinates.getZ() - startCoordinates.getZ());
        return dx * dy * dz;
    }
}
