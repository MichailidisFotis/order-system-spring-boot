package com.example.order_system_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class OrderSystemSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSystemSpringBootApplication.class, args);
	}

}
