package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.dto.ProductDto;
import com.csullagrita.catalogservice.mapper.ProductMapper;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.repository.ProductRepository;
import com.csullagrita.catalogservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

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
    public ResponseEntity modifyProduct(@PathVariable long id, @RequestBody ProductDto productDto) {
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
    public ResponseEntity deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<ProductDto> searchCourse(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                         @RequestParam Optional<Boolean> full,
                                         @SortDefault("id") Pageable pageable) {
        if (full.orElse(false)) {
            return productMapper.productsToDtos(productService.search(predicate, pageable));
        } else {
            return productMapper.productSummariesToDtos(productRepository.findAll(predicate, pageable));
        }
    }
}
