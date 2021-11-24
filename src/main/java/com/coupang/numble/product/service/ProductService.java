package com.coupang.numble.product.service;

import com.coupang.numble.product.dto.ProductDetailDto;
import com.coupang.numble.product.dto.ProductDto;
import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.repository.ProductRepository;
import com.coupang.numble.review.repository.ReviewRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository repository, CategoryService categoryService,
        ReviewRepository reviewRepository) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProductPage(Pageable pageable) {
        return repository.getProductPage(pageable).map(ProductDto::of);
    }

    @Transactional(readOnly = true)
    public ProductDetailDto getProduct(Long productId) {
        Product product =  repository.findById(productId).orElseThrow(() -> new RuntimeException());
        ProductDetailDto productDetailDto = ProductDetailDto.of(product);
        int reviewNum = reviewRepository.productReviewNum(productId);
        int starRate = reviewRepository.productStarRate(productId);
        productDetailDto.setReviewNum(reviewNum);
        productDetailDto.setStarRate(starRate);
        return productDetailDto;
    }

    @Transactional(readOnly = true)
    public List<Product> getCompanyProductLimit4(Long productId, Long companyId) {
        return repository.findFirst4ByCompanyIdAndIdNot(companyId, productId);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getCategoryProductPage(Long categoryId, Pageable pageable) {
        List<Category> childCategoryList = categoryService.getChildCategory(categoryId);
        return repository.getProductPageWithCategory(childCategoryList, pageable)
            .map(ProductDto::of);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getAllCompanyProductPage(Long companyId, Pageable pageable) {
        return repository.getProductPageWithCompanyId(companyId, pageable).map(ProductDto::of);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductPageBySearch(String keyword, boolean rocketFilter, Pageable pageable) {
        if (rocketFilter) {
            return repository.getProductPageBySearchWithRocket(keyword, pageable).map(ProductDto::of);
        }
        return repository.getProductPageBySearch(keyword, pageable).map(ProductDto::of);
    }

}
