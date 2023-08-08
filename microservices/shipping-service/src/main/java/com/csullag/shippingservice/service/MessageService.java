package com.csullag.shippingservice.service;

import com.csullag.shippingservice.model.ShipmentMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MessageService {
    Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final JmsTemplate jmsTemplate;
    private final Random random = new Random();

    public void sendShipmentStatus(long orderId) {
        boolean delivered = random.nextBoolean();
        ShipmentMessage shipmentMessage = new ShipmentMessage(orderId, delivered);
        logger.info(String.format("Sending message: %s", shipmentMessage));

        this.jmsTemplate.convertAndSend("shippingstatus", shipmentMessage);
    }


}
