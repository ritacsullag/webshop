package com.csullagrita.catalogservice.controller;

import com.csullagrita.catalogservice.api.CategoryControllerApi;
import com.csullagrita.catalogservice.api.model.CategoryDto;
import com.csullagrita.catalogservice.mapper.CategoryMapper;
import com.csullagrita.catalogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.naming.NamingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<Object> createNewCategory(CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryMapper.categoryToDto(categoryService.saveCategory(categoryDto.getName())));
        } catch (NamingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
