package com.csullagrita.catalogservice.Dto;

import com.csullagrita.catalogservice.model.Product;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    private long id;

    private String name;

    Set<Product> products;
}
