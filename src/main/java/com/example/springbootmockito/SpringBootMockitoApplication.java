package com.example.springbootmockito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.springbootmockito.model")
public class SpringBootMockitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMockitoApplication.class, args);
    }

}
