package com.csullag.orderservice.service;

import com.csullag.orderservice.model.OrderItem;
import com.csullag.orderservice.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem save(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem modify(OrderItem orderItem){
        if(orderItemRepository.existsById(orderItem.getId())){
            return orderItemRepository.save(orderItem);
        } else {
            throw new NoSuchElementException("Item does not found!");
        }
    }
}
