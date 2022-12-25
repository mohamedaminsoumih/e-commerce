package com.example.orderservice;

import com.example.orderservice.entities.Order;
import com.example.orderservice.entities.ProductItem;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.model.Customer;
import com.example.orderservice.model.Product;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.repositories.ProductItemRepository;
import com.example.orderservice.service.CustomerRESTClientService;
import com.example.orderservice.service.InventoryRESTClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            OrderRepository orderRepository,
            ProductItemRepository productItemRepository,
            CustomerRESTClientService customerRESTClientService,
            InventoryRESTClientService inventoryRESTClientService){
        return args -> {
            List<Customer> customers = customerRESTClientService.allCustomers().getContent().stream().toList();
            List<Product> products = inventoryRESTClientService.allProducts().getContent().stream().toList();
            Long customerId = 1L;
            Customer customer = customerRESTClientService.CustomerById(customerId);
            Random random = new Random();
            for (int i = 0; i < 20; i++) {
                Order order = Order.builder()
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()>0.5? OrderStatus.CREATED:OrderStatus.PENDING)
                        .createdAt(new Date())
                        .build();
                Order save = orderRepository.save(order);
                for (int j = 0; j < products.size(); j++) {
                    if(Math.random()> 0.7){
                        ProductItem productItem = ProductItem.builder()
                                .order(save)
                                .productId(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .qte(1+random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }

                }
            }

        };
    }
}
