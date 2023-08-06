package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.Category;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import com.csullagrita.catalogservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InitDbService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    private final JdbcTemplate jdbcTemplate;

    @Transactional
//    @LogCall
    public void deleteData() {
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Transactional
    public void deleteAudData(){
        jdbcTemplate.update("DELETE FROM product_aud");
        jdbcTemplate.update("DELETE FROM category_aud");
    }

    @Transactional
    public void addInitData() {
        Category category1 = categoryRepository.save(Category.builder().name("game").build());
        Category category2 = categoryRepository.save(Category.builder().name("food").build());
        productRepository.save(Product.builder().name("uno card").price(12.4).category(category1).build());
        productRepository.save(Product.builder().name("banana").price(2.1).category(category2).build());

    }
}
