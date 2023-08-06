package com.csullagrita.catalogservice.mapper;

import com.csullagrita.catalogservice.api.model.CategoryDto;
import com.csullagrita.catalogservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);

    @Named("summary")
    @Mapping(target = "products", ignore = true)
    CategoryDto categorySummaryToDto(Category category);

}
