package com.example.cutsomerservice;

import com.example.cutsomerservice.entities.Customer;
import com.example.cutsomerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CutsomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CutsomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            customerRepository.saveAll(List.of(
                    Customer.builder().name("name1").email("email1").build(),
                    Customer.builder().name("name2").email("email2").build(),
                    Customer.builder().name("name3").email("email3").build()
            ));

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
