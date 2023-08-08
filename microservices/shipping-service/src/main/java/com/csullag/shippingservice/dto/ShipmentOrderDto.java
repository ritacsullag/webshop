package com.csullag.shippingservice.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ShipmentOrderDto {
    public enum ShipmentState {
        SHIPMENT_FAILED,
        DELIVERED
        ;
    }
    long id;
    String deliveryAddress;
    ArrayList<ShipmentItemDto> items;
    String username;
    ShipmentState orderState;

    String addressOfAdmission;
}
