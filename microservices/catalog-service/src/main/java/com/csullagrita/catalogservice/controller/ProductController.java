package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.dto.ProductDto;
import com.csullagrita.catalogservice.mapper.ProductMapper;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/product")
    public ResponseEntity createNewProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productMapper.productToDto(
                    productService.saveProduct(productMapper.dtoToProduct(productDto)))
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/product/{id}")
    public ResponseEntity modifyProduct(@PathVariable long id, @RequestBody ProductDto productDto){
        try {
            Product product = productMapper.dtoToProduct(productDto);
            product.setId(id);
            return ResponseEntity.ok(productMapper.productToDto(
                    productService.modifyProduct(product))
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
