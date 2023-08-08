package com.csullag.orderservice.mapper;

import com.csullag.orderservice.dto.OrderItemDto;
import com.csullag.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem DtoToOrderItem(OrderItemDto itemDto);

    @Mapping(target = "productorder", ignore = true)
    OrderItemDto OrderItemToDto(OrderItem orderItem);
}
