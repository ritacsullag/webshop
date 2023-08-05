package com.csullagrita.catalogservice.mapper;

import com.csullagrita.catalogservice.dto.CategoryDto;
import com.csullagrita.catalogservice.dto.ProductDto;
import com.csullagrita.catalogservice.model.Category;
import com.csullagrita.catalogservice.model.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToDto(Product product);

    Product dtoToProduct(ProductDto productDto);

    @Named("summary")
    @Mapping(ignore = true, target = "category")
    ProductDto productSummaryToDto(Product product);

    @IterableMapping(qualifiedByName = "summary")
    List<ProductDto> productSummariesToDtos(Iterable<Product> findAll);

    List<ProductDto> productsToDtos(Iterable<Product> products);

    @Mapping(target = "products", ignore = true)
    CategoryDto categorySummaryToDto(Category category);
}
