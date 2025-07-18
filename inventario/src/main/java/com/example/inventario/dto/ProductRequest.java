package com.example.inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductRequest {
    @Schema(example = "TV-001")
    private String sku;
    private String name;
    private String description;
    private Integer quantity;
}
