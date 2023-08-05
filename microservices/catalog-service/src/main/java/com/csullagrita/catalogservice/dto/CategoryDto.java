package com.csullagrita.catalogservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    private long id;

    private String name;

    Set<ProductDto> products;
}
