package com.example.orderservice.web;

import com.example.orderservice.entities.Order;
import com.example.orderservice.model.Customer;
import com.example.orderservice.model.Product;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.repositories.ProductItemRepository;
import com.example.orderservice.service.CustomerRESTClientService;
import com.example.orderservice.service.InventoryRESTClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRESTClientService customerRESTClientService;
    private InventoryRESTClientService inventoryRESTClientService;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRESTClientService customerRESTClientService, InventoryRESTClientService inventoryRESTClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRESTClientService = customerRESTClientService;
        this.inventoryRESTClientService = inventoryRESTClientService;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order = orderRepository.findById(id).get();
        Customer customer = customerRESTClientService.CustomerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi -> {
            Product product = inventoryRESTClientService.ProductById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    }
}
