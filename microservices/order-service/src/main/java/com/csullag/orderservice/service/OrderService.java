package com.csullag.orderservice.service;

import com.csullag.orderservice.model.OrderItem;
import com.csullag.orderservice.model.ProductOrder;
import com.csullag.orderservice.model.ProductOrder.State;
import com.csullag.orderservice.repository.OrderRepository;
import com.csullagrita.catalogservice.api.ProductControllerApi;
import com.csullagrita.catalogservice.api.model.ProductDto;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    ProductControllerApi productControllerApi;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;

    @Transactional
    public ProductOrder save(ProductOrder productorder) {
        productorder.setState(State.PENDING);
        Set<OrderItem> savedItems = new HashSet<>();

//        Set<OrderItem> items = new HashSet<>();
//        for (OrderItem orderItem : productorder.getItems()) {
//            ResponseEntity<List<ProductDto>> listResponseEntity = productControllerApi.searchProduct(orderItem.getName(), List.of(0.0, orderItem.getPrice()), null, null, null, null);
//        }

        Set<OrderItem> items = productorder.getItems();
        productorder.setItems(null);
        items.forEach(orderItem -> savedItems.add(orderItemService.save(orderItem)));

        ProductOrder savedOrder = orderRepository.save(productorder);
        savedItems.forEach(savedOrder::addOrderItem);

        return orderRepository.save(productorder);
    }

    @Transactional
    public List<ProductOrder> searchOrderByName(String username) {
        return orderRepository.findByUsername(username);
    }

    @Transactional
    public ProductOrder processStatus(long orderId, boolean isConfirmed) {
        ProductOrder order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (isConfirmed) {
            order.setState(State.CONFIRMED);
            int deliveryId = shipmentService.createNewDelivery(order);
            logger.info(String.format("Shipment-service sent the shipmentId: %s", deliveryId));
        } else {
            order.setState(State.DECLINED);
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void setDeliveryStatus(long orderId, Boolean delivered){
        ProductOrder order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (Boolean.TRUE.equals(delivered)) {
            order.setState(State.DELIVERED);
        } else {
            order.setState(State.SHIPMENT_FAILED);
        }
        orderRepository.save(order);
    }
}
