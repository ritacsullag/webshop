package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.Category;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;

@Service
@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category saveCategory(String categoryName) throws NamingException {
        if(categoryRepository.existsByName(categoryName)){
            throw new NamingException("Category name already exists!");
        }
        if(categoryName.isEmpty()){
            throw new InvalidNameException("Category name can not be empty!");
        }
        return categoryRepository.save(Category.builder().name(categoryName).build());

    }
}
