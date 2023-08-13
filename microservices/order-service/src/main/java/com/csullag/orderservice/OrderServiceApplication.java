package com.csullag.orderservice;

import com.csullagrita.catalogservice.api.ProductControllerApi;
import com.csullagrita.tokenlib.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = {ProductControllerApi.class})
@SpringBootApplication(scanBasePackageClasses = {OrderServiceApplication.class, JwtAuthFilter.class})
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
