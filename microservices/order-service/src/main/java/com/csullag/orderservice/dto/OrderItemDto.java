package com.csullag.orderservice.dto;

import com.csullag.orderservice.model.ProductOrder;
import lombok.Data;

@Data
public class OrderItemDto {

    private long id;

    private long quantity;

    private double price;

    private String name;

    private String category;

    private long productId;

    private ProductOrder productorder;
}
