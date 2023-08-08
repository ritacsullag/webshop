package com.csullag.shippingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentMessage {

    private long orderId;
    private boolean delivered;
}
