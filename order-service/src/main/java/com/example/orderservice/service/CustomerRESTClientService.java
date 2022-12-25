package com.example.orderservice.service;

import com.example.orderservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customer-service") // Contact Discovery
public interface CustomerRESTClientService {
    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Customer> allCustomers();
    @GetMapping("/customers/{id}?projection=fullCustomer")
    public Customer CustomerById(@PathVariable Long id);
}
