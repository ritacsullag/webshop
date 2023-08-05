package com.csullagrita.catalogservice.dto;

import lombok.Data;

@Data
public class ProductDto {

    private long id;

    private String name;

    private double price;

    CategoryDto category;
}
