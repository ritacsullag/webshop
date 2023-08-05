package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.Category;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import com.csullagrita.catalogservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InitDbService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional
//    @LogCall
    public void deleteData() {
        categoryRepository.deleteAll();

    }

//    @Transactional
//    public void deleteAudTables(){
//        jdbcTemplate.update("DELETE FROM address_aud");
//        jdbcTemplate.update("DELETE FROM airport_aud");
//        jdbcTemplate.update("DELETE FROM flight_aud");
//    }

    @Transactional
    public void addInitData() {
        Category category = categoryRepository.save(Category.builder().name("nice").build());
        Product product = productRepository.save(Product.builder().name("game").price(45.6).category(category).build());

    }
}
