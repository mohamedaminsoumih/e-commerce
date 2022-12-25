package com.example.orderservice.service;

import com.example.orderservice.model.Customer;
import com.example.orderservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "inventory-service") // Contact Discovery
public interface InventoryRESTClientService {
    @GetMapping("/products?projection=fullProduct")
    public PagedModel<Product> allProducts();
    @GetMapping("/products/{id}?projection=fullProduct")
    public Product ProductById(@PathVariable Long id);
}
