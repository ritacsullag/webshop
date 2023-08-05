package com.csullagrita.catalogservice.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "Product.category", attributeNodes = @NamedAttributeNode("category"))
public class Product {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private long id;

    @ToString.Include
    private String name;

    private double price;

    @ManyToOne
    Category category;
}
