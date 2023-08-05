package com.csullagrita.catalogservice.dto;

import lombok.Data;

@Data
public class ProductPriceHistoryDto {

    private long id;

    private String name;

    private double price;
}
