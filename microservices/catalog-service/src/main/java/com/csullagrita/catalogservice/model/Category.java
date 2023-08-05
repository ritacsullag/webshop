package com.csullagrita.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Category {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private long id;

    private String name;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    Set<Product> products;

    public void addProductToCategory(Product product) {
        product.setCategory(this);
        if (this.products == null)
            this.products = new HashSet<>();
        this.products.add(product);
    }

    public void removeLineItem(Product product) {
        products.remove(product);
    }
}
