package com.csullagrita.catalogservice.repository;

import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.model.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslBinderCustomizer<QProduct>,
        QuerydslWithEntityGraphRepository<Product, Long>,
        QuerydslPredicateExecutor<Product> {

    @Override
    default void customize(QuerydslBindings bindings, QProduct product) {
//        product name with like and ignore case everywh ere in the word
        bindings.bind(product.name).first((path, value) -> path.containsIgnoreCase(value));
//        category name case-insensitive, like, prefix
        bindings.bind(product.category.name).first((path, value) -> path.startsWithIgnoreCase(value));
//        price from to, both optional
        bindings.bind(product.price).all((path, values) -> {
            if (values.size() != 2) {
                return Optional.empty();
            }
            Iterator<? extends Double> iterator = values.iterator();
            Double from = iterator.next();
            Double to = iterator.next();
            return Optional.of(path.between(from, to));
        });
    }


}
