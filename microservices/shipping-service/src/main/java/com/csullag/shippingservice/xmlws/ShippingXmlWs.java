package com.csullag.shippingservice.xmlws;

import com.csullag.shippingservice.dto.ShipmentOrderDto;
import jakarta.jws.WebService;

@WebService
public interface ShippingXmlWs {

    public int createNewDelivery(ShipmentOrderDto order);
}
