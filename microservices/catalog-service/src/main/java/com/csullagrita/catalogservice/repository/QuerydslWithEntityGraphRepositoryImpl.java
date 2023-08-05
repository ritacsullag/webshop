package com.csullagrita.catalogservice.repository;

import com.csullagrita.catalogservice.model.Product;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import java.util.List;


public class QuerydslWithEntityGraphRepositoryImpl
        extends SimpleJpaRepository<Product, Long>
        implements QuerydslWithEntityGraphRepository<Product, Long> {

    private final EntityManager entityManager;
    private final EntityPath<Product> path;
    private final PathBuilder<Product> builder;
    private final Querydsl querydsl;


    public QuerydslWithEntityGraphRepositoryImpl(EntityManager em) {
        super(Product.class, em);
        this.entityManager = em;
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(Product.class);
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(em, builder);
    }


    @Override
    public List<Product> findAll(Predicate predicate, String entityGraphName, Sort sort) {
//        lapozas nelkul:
//        JPAQuery query =
//                (JPAQuery) createQuery(predicate).select(path);

//        lapozasnal mar bele kell tenni a sortolast, kulonben nincs garancia hogy ket egymást követően napozás helyesen működik
        JPAQuery query =
                (JPAQuery) querydsl.applySorting(sort, createQuery(predicate).select(path));
        query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(), entityManager.getEntityGraph(entityGraphName));
        return query.fetch();
    }

    private JPAQuery createQuery(Predicate predicate) {
        return querydsl.createQuery(path).where(predicate);
    }

}


