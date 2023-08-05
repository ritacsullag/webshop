package com.csullagrita.catalogservice;

import com.csullagrita.catalogservice.service.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class CatalogServiceApplication implements CommandLineRunner {

    private final InitDbService dbInitService;

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dbInitService.deleteData();
        dbInitService.deleteAudData();
        dbInitService.addInitData();
    }
}
