package com.csullag.shippingservice.xmlws;

import com.csullag.shippingservice.dto.ShipmentOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{

    private final Random random = new Random();

    @Override
    public int createNewDelivery(ShipmentOrderDto order) {
        return 0;
    }
}
