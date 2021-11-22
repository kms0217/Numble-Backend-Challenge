package com.coupang.numble.product.service;

import com.coupang.numble.product.dto.ProductDetailDto;
import com.coupang.numble.product.dto.ProductDto;
import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
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

    public Page<ProductDto> getAllProductPage(Pageable pageable) {
        String sql = "SELECT DISTINCT P FROM Product P";
        int total = repository.countAll();
        List<Product> products = getProductPageFetchJoinThumbnailUrls(pageable, sql);
        List<ProductDto> productDtos =
            products.stream().map(ProductDto::of).collect(Collectors.toList());
        return new PageImpl<>(productDtos, pageable, total);
    }

    public ProductDetailDto getProduct(Long productId) {
        Product product =  repository.findById(productId).orElseThrow(() -> new RuntimeException());
        return ProductDetailDto.of(product);
    }

    public List<Product> getCompanyProductLimit4(Long productId, Long companyId) {
        return repository.find4ProductByCompanyIdAndNotId(companyId, productId);
    }

    public Page<ProductDto> getCategoryProductPage(Long categoryId, Pageable pageable) {
        String sql = "SELECT DISTINCT P FROM Product P WHERE P.type in :param";
        List<Category> childCategoryList = categoryService.getChildCategory(categoryId);
        int total = repository.countAllByCategories(childCategoryList);
        List<Product> products =
            getProductPageFetchJoinThumbnailUrlsWithParam(pageable, sql, childCategoryList);
        List<ProductDto> productDtos =
            products.stream().map(ProductDto::of).collect(Collectors.toList());
        return new PageImpl<>(productDtos, pageable, total);
    }

    public Page<ProductDto> getAllCompanyProductPage(Long companyId, Pageable pageable) {
        String sql = "SELECT DISTINCT P FROM Product P WHERE P.company.id = :param";
        int total = repository.countAllByCompanyId(companyId);
        List<Product> products =
            getProductPageFetchJoinThumbnailUrlsWithParam(pageable, sql, companyId);
        List<ProductDto> productDtos =
            products.stream().map(ProductDto::of).collect(Collectors.toList());
        return new PageImpl<>(productDtos, pageable, total);
    }

    private List<Product> getProductPageFetchJoinThumbnailUrlsWithParam(
        Pageable pageable,
        String sql,
        Object param
    ) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        List<Product> products = em.createQuery(sql, Product.class)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .setParameter("param", param)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        fetchJoinThumbnailUrls(products);
        return products;
    }

    private List<Product> getProductPageFetchJoinThumbnailUrls(Pageable pageable, String sql) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        List<Product> products = em.createQuery(sql, Product.class)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        fetchJoinThumbnailUrls(products);
        return products;
    }

    private void fetchJoinThumbnailUrls(List<Product> products) {
        products = em.createQuery("SELECT DISTINCT P FROM Product P LEFT JOIN FETCH P.thumbnailUrls where P in :products", Product.class)
            .setParameter("products", products)
            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

}
