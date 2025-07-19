package com.example.inventario.controller;

import com.example.inventario.dto.InventoryResponse;
import com.example.inventario.dto.RegisterProductsRequest;
import com.example.inventario.dto.RegisterWarehouseRequest;
import com.example.inventario.dto.WarehouseResponse;
import com.example.inventario.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Inventory API", description = "Manage warehouse inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping(value = "/inventory", headers = "X-API-VERSION=1")
    @Operation(summary = "Get inventory by warehouse")
    public ResponseEntity<CollectionModel<InventoryResponse>> getInventory(@RequestParam("sedeId") Long warehouseId) {
        List<InventoryResponse> responses = inventoryService.getInventoryByWarehouse(warehouseId);
        responses.forEach(r -> {
            Link self = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryController.class)
                    .getInventory(warehouseId)).withSelfRel();
            r.add(self);
        });
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventoryController.class)
                .getInventory(warehouseId)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(responses, link));
    }

    @PostMapping(value = "/inventory", headers = "X-API-VERSION=1")
    @Operation(summary = "Register products in warehouse")
    public ResponseEntity<Void> registerProducts(@RequestBody RegisterProductsRequest request) {
        inventoryService.registerProducts(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/inventory/warehouse", headers = "X-API-VERSION=1")
    @Operation(summary = "Register warehouse")
    public ResponseEntity<Void> registerWarehouse(@RequestBody RegisterWarehouseRequest request) {
        inventoryService.registerWarehouse(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/inventory/warehouse", headers = "X-API-VERSION=1")
    @Operation(summary = "Get all warehouses")
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        List<WarehouseResponse> responses = inventoryService.getAllWarehouses();

        return ResponseEntity.ok(responses);
    }

}

