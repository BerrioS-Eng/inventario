package com.example.inventario.service;

import com.example.inventario.dto.InventoryResponse;
import com.example.inventario.dto.RegisterProductsRequest;
import com.example.inventario.dto.RegisterWarehouseRequest;
import com.example.inventario.dto.WarehouseResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getInventoryByWarehouse(Long warehouseId);
    void registerProducts(RegisterProductsRequest request);
    void registerWarehouse(RegisterWarehouseRequest request);
    List<WarehouseResponse> getAllWarehouses();
}
