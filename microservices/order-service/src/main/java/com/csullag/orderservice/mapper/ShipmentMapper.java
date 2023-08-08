package com.csullag.orderservice.mapper;

import com.csullag.orderservice.model.ProductOrder;
import com.csullag.shippingservice.xmlws.ShipmentOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentOrderDto orderToShipmentOrderDto(ProductOrder order);
}
