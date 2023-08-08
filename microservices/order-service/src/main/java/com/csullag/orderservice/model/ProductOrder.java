package com.csullag.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ProductOrder {

    public enum State {
        PENDING,
        CONFIRMED,
        DECLINED,
        SHIPMENT_FAILED,
        DELIVERED
    }

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private long id;

    @OneToMany(mappedBy = "productorder")
    @JsonIgnore
    private Set<OrderItem> items;

    private String address;

    private String username;

    private State state;

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setProductorder(this);
        if(this.items == null)
            this.items = new HashSet<>();
        this.items.add(orderItem);
    }
}
