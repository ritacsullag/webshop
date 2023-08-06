package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.api.model.ProductDto;
import com.csullagrita.catalogservice.api.model.ProductPriceHistoryDto;
import com.csullagrita.catalogservice.mapper.ProductMapper;
import com.csullagrita.catalogservice.model.HistoryData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/products")
public class ProductControllerWithoutOpenApi {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @PostMapping("/product")
    public ResponseEntity<Object> createNewProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productMapper.productToDto(
                    productService.saveProduct(productMapper.dtoToProduct(productDto)))
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<Object> modifyProduct(@PathVariable long id, @RequestBody ProductDto productDto) {
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
    public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<ProductDto> searchProduct(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                          @RequestParam Optional<Boolean> full,
                                          @SortDefault("id") Pageable pageable) {
        if (full.orElse(false)) {
            return productMapper.productsToDtos(productService.search(predicate, pageable));
        } else {
            return productMapper.productSummariesToDtos(productRepository.findAll(predicate, pageable));
        }
    }

    @GetMapping("/pricehistory/{id}")
    public List<HistoryData<ProductPriceHistoryDto>> getProductHistoryById(@PathVariable("id") long productId) {
        List<HistoryData<Product>> productHistories = productService.getProductHistoryWithRelation(productId);
        List<HistoryData<ProductPriceHistoryDto>> productDtoHistories = new ArrayList<>();

        productHistories
                .forEach(
                        productHistory -> productDtoHistories.add(
                                new HistoryData<>(
                                        productMapper.productToProductHistoryDto(productHistory.getData()),
                                        productHistory.getDate()
                                )
                        )
                );

        return productDtoHistories;
    }
}
