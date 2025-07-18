package com.example.inventario.service;

import com.example.inventario.dto.InventoryResponse;
import com.example.inventario.dto.ProductRequest;
import com.example.inventario.dto.RegisterProductsRequest;
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
    public void registerProducts(RegisterProductsRequest request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        for (ProductRequest p : request.getProducts()) {
            Product product = productRepository.findBySku(p.getSku());
            if (product == null) {
                product = Product.builder()
                        .sku(p.getSku())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build();
                productRepository.save(product);
            }
            Inventory inventory = Inventory.builder()
                    .warehouse(warehouse)
                    .product(product)
                    .quantity(p.getQuantity())
                    .build();
            inventoryRepository.save(inventory);
        }
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
