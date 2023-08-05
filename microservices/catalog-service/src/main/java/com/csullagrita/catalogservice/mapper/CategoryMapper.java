package com.csullagrita.catalogservice.mapper;

import com.csullagrita.catalogservice.dto.CategoryDto;
import com.csullagrita.catalogservice.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);

}
