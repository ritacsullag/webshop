package com.csullagrita.catalogservice.dto;

import com.csullagrita.catalogservice.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    private long id;

    private String name;

    Set<Product> products;
}
