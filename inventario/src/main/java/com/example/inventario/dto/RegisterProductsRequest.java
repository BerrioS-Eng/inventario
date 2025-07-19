package com.example.inventario.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterProductsRequest {
    private String warehouseName; // Nuevo campo
    private List<ProductRequest> products;
}


