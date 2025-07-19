package com.example.inventario.service;

import com.example.inventario.dto.*;
import com.example.inventario.model.Inventory;
import com.example.inventario.model.Product;
import com.example.inventario.model.Warehouse;
import com.example.inventario.repository.InventoryRepository;
import com.example.inventario.repository.ProductRepository;
import com.example.inventario.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventoryByWarehouse(Long warehouseId) {
        return inventoryRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerWarehouse(RegisterWarehouseRequest request) {
        Warehouse warehouse = Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
        warehouseRepository.save(warehouse);
    }

    @Override
    @Transactional
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(this::toWarehouseResponse)
                .collect(Collectors.toList());
    }

    private WarehouseResponse toWarehouseResponse(Warehouse warehouse) {
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .build();
    }



    @Override
    @Transactional
    public void registerProducts(RegisterProductsRequest request) {
        Warehouse warehouse = warehouseRepository.findByName(request.getWarehouseName())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + request.getWarehouseName()));

        for (ProductRequest productRequest : request.getProducts()) {
            Product existingProduct = getOrCreateProduct(productRequest);
            createAndSaveInventory(warehouse, existingProduct, productRequest.getQuantity());
        }
    }

    private Product getOrCreateProduct(ProductRequest productRequest) {
        Product product = productRepository.findBySku(productRequest.getSku());
        if (product == null) {
            product = Product.builder()
                    .sku(productRequest.getSku())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .build();
            productRepository.save(product);
        }
        return product;
    }

    private void createAndSaveInventory(Warehouse warehouse, Product product, Integer quantity) {
        Inventory inventory = Inventory.builder()
                .warehouse(warehouse)
                .product(product)
                .quantity(quantity)
                .build();
        inventoryRepository.save(inventory);
    }



    private InventoryResponse toResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .sku(inventory.getProduct().getSku())
                .name(inventory.getProduct().getName())
                .description(inventory.getProduct().getDescription())
                .quantity(inventory.getQuantity())
                .build();
    }
}
