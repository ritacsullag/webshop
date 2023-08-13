package com.csullag.orderservice.controller;

import com.csullag.orderservice.dto.OrderDto;
import com.csullag.orderservice.mapper.OrderMapper;
import com.csullag.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    public final OrderService orderService;
    public final OrderMapper orderMapper;

    @PostMapping("/order")
    public OrderDto saveOrder(@RequestBody OrderDto orderDto){
        return orderMapper.orderToDto(orderService.save(orderMapper.dtoToOrder(orderDto)));
    }

    @PreAuthorize("hasAuthority('admin') or #username == authentication.principal.username")
    @GetMapping("/order")
    public List<OrderDto> searchOrderByName(@RequestParam String username){
        return orderMapper.ordersToDto(orderService.searchOrderByName(username));
    }

    @PutMapping("/order/{id}")
    public OrderDto processOrderStatus(@PathVariable("id") long orderId, @RequestParam boolean isConfirmed){
        return orderMapper.orderSummaryToDto(orderService.processStatus(orderId, isConfirmed));
    }
}
