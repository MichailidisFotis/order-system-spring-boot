package com.example.order_system_spring_boot.configs;

import com.example.order_system_spring_boot.middlewares.RequireLogin;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfigs {


    @Bean
    public FilterRegistrationBean<RequireLogin> authFilter(RequireLogin filter) {
        FilterRegistrationBean<RequireLogin> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/users/*");
        registration.addUrlPatterns("/customers/*");
        registration.addUrlPatterns("/categories/*");
        registration.addUrlPatterns("/products/*");
        registration.addUrlPatterns("/orders/*");
        registration.setOrder(1);
        return registration;
    }


}
