package com.example.inventario.repository;

import com.example.inventario.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseId(Long warehouseId);
}
