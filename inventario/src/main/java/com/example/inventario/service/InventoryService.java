package com.example.inventario.service;

import com.example.inventario.dto.InventoryResponse;
import com.example.inventario.dto.RegisterProductsRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getInventoryByWarehouse(Long warehouseId);
    void registerProducts(RegisterProductsRequest request);
}
