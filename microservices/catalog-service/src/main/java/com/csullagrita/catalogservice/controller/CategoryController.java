package com.csullagrita.catalogservice.controller;


import com.csullagrita.catalogservice.dto.CategoryDto;
import com.csullagrita.catalogservice.mapper.CategoryMapper;
import com.csullagrita.catalogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<?> createNewCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryMapper.categoryToDto(categoryService.saveCategory(categoryDto.getName())));
        } catch (NamingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
