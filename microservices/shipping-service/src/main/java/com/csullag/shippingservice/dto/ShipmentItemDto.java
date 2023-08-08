package com.csullag.shippingservice.dto;

import lombok.Data;

@Data
public class ShipmentItemDto {
    long id;
    long quantity;
    double orderPrice;
    ShipmentOrderDto order;

    String productname;
    long productId;
}
