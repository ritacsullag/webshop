package com.csullag.orderservice.service;

import com.csullag.orderservice.mapper.ShipmentMapper;
import com.csullag.orderservice.model.ProductOrder;
import com.csullag.shippingservice.xmlws.ShipmentOrderDto;
import com.csullag.shippingservice.xmlws.ShippingXmlWs;
import com.csullag.shippingservice.xmlws.ShippingXmlWsImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentMapper shipmentMapper;

    public int createNewDelivery(ProductOrder order) {
        ShipmentOrderDto shipmentDto = shipmentMapper.orderToShipmentOrderDto(order);
        ShippingXmlWs shippingXmlWsImplPort = new ShippingXmlWsImplService().getShippingXmlWsImplPort();
        return shippingXmlWsImplPort.createNewDelivery(shipmentDto);
    }
}
