package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.Category;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import java.util.NoSuchElementException;

@Service
@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveCategory(String categoryName) throws NamingException {
        if (categoryRepository.existsByName(categoryName)) {
            throw new NamingException("Category name already exists!");
        }
        if (categoryName.isEmpty()) {
            throw new InvalidNameException("Category name can not be empty!");
        }
        return categoryRepository.save(Category.builder().name(categoryName).build());

    }

    @Transactional
    public Category update(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            return categoryRepository.save(category);
        } else {
            throw new NoSuchElementException();
        }
    }
}
