package com.csullag.orderservice.jms;

import com.csullag.orderservice.model.ShipmentMessage;
import com.csullag.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentMessageConsumer {

    Logger logger = LoggerFactory.getLogger(ShipmentMessageConsumer.class);
    private final OrderService orderService;

    @JmsListener(destination = "shippingstatus")
    public void onShippingStatusMessage(ShipmentMessage message) {
        logger.info(String.format("Message is: %s", message.toString()));
        Boolean delivered = message.isDelivered();
        long orderId = message.getOrderId();
        orderService.setDeliveryStatus(orderId, delivered.booleanValue());
    }
}
