package com.csullagrita.catalogservice.mapper;

import com.csullagrita.catalogservice.Dto.ProductDto;
import com.csullagrita.catalogservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToDto(Product product);

    Product dtoToProduct(ProductDto productDto);
}
