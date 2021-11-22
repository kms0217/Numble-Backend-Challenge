package com.coupang.numble.product.service;

import com.coupang.numble.product.entity.Category;
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
    private final CategoryService categoryService;
    private final EntityManager em;

    public ProductService(ProductRepository repository,
        CategoryService categoryService, EntityManager em) {
        this.repository = repository;
        this.categoryService = categoryService;
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
        fetchJoinAllOneToManyEntityInList(productList);
        return new PageImpl<>(productList, pageable, total);
    }

    public Product getProduct(Long productId) {
        return repository.findById(productId)
            .orElseThrow(() -> new RuntimeException());
    }

    public List<Product> getCompanyProductLimit4(Long productId, Long companyId) {
        return repository.findTop4ByCompanyIdAndNotId(companyId, productId);
    }

    public Page<Product> getCategoryProductPage(Long categoryId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        List<Category> childCategoryIdList = categoryService.getChildCategoryId(categoryId);
        int total = repository.countAllByCategories(childCategoryIdList);
        List<Product> productList = em.createQuery("SELECT DISTINCT P FROM Product P WHERE P.type in :categoryList", Product.class)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .setParameter("categoryList", childCategoryIdList)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        fetchJoinAllOneToManyEntityInList(productList);
        return new PageImpl<>(productList, pageable, total);
    }

    public Object getAllCompanyProductPage(Long companyId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        int total = repository.countAllByCompanyId(companyId);
        List<Product> productList = em.createQuery("SELECT DISTINCT P FROM Product P WHERE P.company.id = :companyId", Product.class)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .setParameter("companyId", companyId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        fetchJoinAllOneToManyEntityInList(productList);
        return new PageImpl<>(productList, pageable, total);
    }

    private void fetchJoinAllOneToManyEntityInList(List<Product> productList) {
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
    }


}
