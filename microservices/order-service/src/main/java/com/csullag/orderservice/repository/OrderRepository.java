package com.csullag.orderservice.repository;

import com.csullag.orderservice.model.ProductOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Long> {

    @EntityGraph(attributePaths = {"items"})
    List<ProductOrder> findByUsername(String username);
}
