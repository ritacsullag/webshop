package com.csullag.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private long id;

    @EqualsAndHashCode.Include()
    private long quantity;

    @EqualsAndHashCode.Include()
    private double price;

    private String name;

    private String category;

    private long productId;

    @ManyToOne
    @JsonIgnore
    private ProductOrder productorder;
}
