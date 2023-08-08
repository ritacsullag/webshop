package com.csullag.shippingservice.xmlws;

import com.csullag.shippingservice.dto.ShipmentOrderDto;
import com.csullag.shippingservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{

    private final MessageService messageService;
    private final Random random = new Random();

    Logger logger = LoggerFactory.getLogger(ShippingXmlWsImpl.class);

    @Override
    public int createNewDelivery(ShipmentOrderDto order) {
        logger.info(String.format("Shipment id sender thread:  %s", Thread.currentThread().getName()));
        messageService.sendShipmentStatus(order.getId());

        return random.nextInt(10000, 99999);
    }
}
