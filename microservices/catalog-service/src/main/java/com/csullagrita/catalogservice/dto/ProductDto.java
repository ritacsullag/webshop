package com.csullagrita.catalogservice.dto;

import com.csullagrita.catalogservice.model.Category;
import lombok.Data;

@Data
public class ProductDto {

    private long id;

    private String name;

    private double price;

    Category category;
}