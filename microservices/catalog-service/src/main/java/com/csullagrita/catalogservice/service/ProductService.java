package com.csullagrita.catalogservice.service;

import com.csullagrita.catalogservice.model.Product;
import com.csullagrita.catalogservice.model.QProduct;
import com.csullagrita.catalogservice.repository.CategoryRepository;
import com.csullagrita.catalogservice.repository.ProductRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
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

    public List<Product> search(Predicate predicate, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(predicate, pageable);
        BooleanExpression productIdInPredicate = QProduct.product.in(productPage.getContent());

//        List<Product> products = productRepository.findAll(productIdInPredicate, "Product.category", Sort.unsorted());
        List<Product> products = productRepository.findAll(productIdInPredicate, "Product.category", pageable.getSort());
        return products;
    }

    ;
}
