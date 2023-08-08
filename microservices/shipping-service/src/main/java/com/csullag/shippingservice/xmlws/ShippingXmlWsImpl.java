package com.csullag.shippingservice.xmlws;

import com.csullag.shippingservice.dto.ShipmentOrderDto;
import com.csullag.shippingservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{

    private final MessageService messageService;

    @Override
    public int createNewDelivery(ShipmentOrderDto order) {
        messageService.sendShipmentStatus(order.getId());
        return 0;
    }
}
