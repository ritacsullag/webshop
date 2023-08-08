package com.csullag.orderservice.mapper;

import com.csullag.orderservice.dto.OrderDto;
import com.csullag.orderservice.dto.OrderItemDto;
import com.csullag.orderservice.model.ProductOrder;
import com.csullag.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productorder",ignore = true)
    OrderItem dtoToOrderItem(OrderItemDto itemDto);

    @Mapping(target = "productorder",ignore = true)
    OrderItemDto orderItemToDto(OrderItem orderItem);

    ProductOrder dtoToOrder(OrderDto orderDto);

    List<OrderDto> ordersToDto(List<ProductOrder> productOrders);

    OrderDto orderToDto(ProductOrder order);

    //processOrder vegponton, nem szukseges latni a termekeket
    @Named("summary")
    @Mapping(target = "items", ignore = true)
    OrderDto orderSummaryToDto(ProductOrder order);

}
