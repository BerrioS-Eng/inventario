package com.example.inventario.repository;

import com.example.inventario.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySku(String sku);
}
