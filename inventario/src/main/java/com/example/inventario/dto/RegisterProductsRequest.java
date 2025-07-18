package com.example.inventario.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterProductsRequest {
    private Long warehouseId;
    private List<ProductRequest> products;
}

