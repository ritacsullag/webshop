package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.mapper.ProductMapper;
import com.csullagrita.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
}
