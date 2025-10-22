package com.paklog.digitaltwin.application.command;

import com.paklog.digitaltwin.domain.valueobject.Coordinates3D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWarehouseModelCommand {
    private String warehouseId;
    private String name;
    private String description;
    private Coordinates3D dimensions;
}
