package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.HistoryData;
import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.model.QProduct;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import com.csullagrita.catalogservice.repository.ProductRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Component
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public Product saveProduct(Product product) {
//      csak letezo kategoriahoz lehet lementeni
        product.setCategory(categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new NoSuchElementException("Category does not exist!")));
        return productRepository.save(product);
    }


    @Transactional
    public void deleteProduct(long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Product does not exist");
        }
    }

    @Transactional
    public Product modifyProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            return productRepository.save(product);
        } else {
            throw new NoSuchElementException("Product does not exist");
        }
    }

    @Transactional
    public List<Product> search(Predicate predicate, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(predicate, pageable);
        BooleanExpression productIdInPredicate = QProduct.product.in(productPage.getContent());

//        List<Product> products = productRepository.findAll(productIdInPredicate, "Product.category", Sort.unsorted());
        List<Product> products = productRepository.findAll(productIdInPredicate, "Product.category", pageable.getSort());
        return products;
    }

    @Transactional
    public List<HistoryData<Product>> getProductHistoryWithRelation(long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<HistoryData<Product>> historyDataList = auditReader
                .createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.property("id").eq(productId))
                .getResultList()
                .stream()
                .map(o -> {
                    Object[] objArray = (Object[]) o;
                    DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) objArray[1];
                    Product product = (Product) objArray[0];

                    return new HistoryData<>(
                            product,
                            revisionEntity.getRevisionDate()
                    );
                }).toList();

        return historyDataList;
    }

}
