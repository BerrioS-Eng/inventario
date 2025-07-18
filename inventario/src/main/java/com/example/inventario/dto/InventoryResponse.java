package com.example.inventario.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class InventoryResponse extends RepresentationModel<InventoryResponse> {
    private String sku;
    private String name;
    private String description;
    private Integer quantity;
}
