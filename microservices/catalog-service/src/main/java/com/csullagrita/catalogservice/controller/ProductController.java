package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.api.ProductControllerApi;
import com.csullagrita.catalogservice.api.model.HistoryDataProductPriceHistoryDto;
import com.csullagrita.catalogservice.api.model.ProductDto;
import com.csullagrita.catalogservice.mapper.HistoryDataMapper;
import com.csullagrita.catalogservice.mapper.ProductMapper;
import com.csullagrita.catalogservice.model.HistoryData;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.repository.ProductRepository;
import com.csullagrita.catalogservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final HistoryDataMapper historyDataMapper;
    private final ProductRepository productRepository;
    private final PageableHandlerMethodArgumentResolver pageableResolver;
    private final QuerydslPredicateArgumentResolver predicateResolver;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<Object> createNewProduct(ProductDto productDto) {
        try {
            return ResponseEntity.ok(productMapper.productToDto(
                    productService.saveProduct(productMapper.dtoToProduct(productDto)))
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<HistoryDataProductPriceHistoryDto>> getProductHistoryById(Long id) {
        List<HistoryData<Product>> productHistories = productService.getProductHistoryWithRelation(id);
        List<HistoryDataProductPriceHistoryDto> productDtoHistories = new ArrayList<>();

        productHistories
                .forEach(
                        productHistory -> productDtoHistories.add(
                                historyDataMapper.productHistoryDataToDto(productHistory
                                )
                        )
                );

        return ResponseEntity.ok(productDtoHistories);
    }

    @Override
    public ResponseEntity<Object> modifyProduct(Long id, ProductDto productDto) {
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

    //Pageable
    public void configPageable(@SortDefault("id") Pageable pageable) {
    }

    ;

    private Pageable createPageable() {
        Pageable pageable;
        Method method = null;
        try {
            method = this.getClass().getMethod("configPageable", Pageable.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        MethodParameter methodParameter = new MethodParameter(method, 0);
        pageable = pageableResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
        return pageable;
    }

    //Predicte
    public void configurePredicate(@QuerydslPredicate(root = Product.class) Predicate predicate) {
    }

    ;

    private Predicate createPredicate() {
        Method method;
        try {
            method = this.getClass().getMethod("configurePredicate", Predicate.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);
            return (Predicate) predicateResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ResponseEntity<List<ProductDto>> searchProduct(String name, List<Double> price, Boolean full, Integer page, Integer size, List<String> sort) {
        Pageable pageable = createPageable();
        Predicate predicate = createPredicate();
        boolean isFull = full == null ? false : full;

        if (isFull) {
            return ResponseEntity.ok(productMapper.productsToDtos(productService.search(predicate, pageable)));
        } else {
            return ResponseEntity.ok(productMapper.productSummariesToDtos(productRepository.findAll(predicate, pageable)));
        }
    }
}
