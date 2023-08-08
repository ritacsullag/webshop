package com.csullag.orderservice.dto;

import com.csullag.orderservice.model.ProductOrder.State;
import com.csullag.orderservice.model.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDto {

    private long id;

    private Set<OrderItem> items;

    private String address;

    private String username;

    private State state;
}
