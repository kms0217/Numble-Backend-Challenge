package com.coupang.numble.product.service;

import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.repository.ProductRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final EntityManager em;

    public ProductService(ProductRepository repository, EntityManager em) {
        this.repository = repository;
        this.em = em;
    }

    public Page<Product> getAllProductPage(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        int total = repository.countAll();
        List<Product> productList = em.createQuery("SELECT DISTINCT P FROM Product P", Product.class)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();

        productList = em.createQuery("SELECT DISTINCT P FROM Product P LEFT JOIN FETCH P.optionSet where P in :products", Product.class)
            .setParameter("products", productList)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .getResultList();

        productList = em.createQuery("SELECT DISTINCT P FROM Product P LEFT JOIN FETCH P.clothesOptions where P in :products", Product.class)
            .setParameter("products", productList)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .getResultList();

        productList = em.createQuery("SELECT DISTINCT P FROM Product P LEFT JOIN FETCH P.thumbnailUrls where P in :products", Product.class)
            .setParameter("products", productList)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .getResultList();

        return new PageImpl<>(productList, pageable, total);
    }
}
