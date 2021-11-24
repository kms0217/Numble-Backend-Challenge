package com.coupang.numble.product.repository;

import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.Product;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT COUNT(P) FROM Product P")
    int countAll();

    @EntityGraph(attributePaths = {"type"}, type = EntityGraphType.LOAD)
    Optional<Product> findById(Long productId);

    List<Product> findFirst4ByCompanyIdAndIdNot(@Param("companyId") Long companyId, @Param("productId") Long productId);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.title LIKE %:keyword%")
    int countAllByFilter(@Param("keyword") String keyword);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.title LIKE %:keyword% AND P.rocketShipping = true")
    int countAllByFilterWithRocket(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p JOIN ProductImage pi ON p = pi.product")
    Page<Product> getProductPage(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN ProductImage pi ON p = pi.product WHERE p.type in :category")
    Page<Product> getProductPageWithCategory(@Param("category") List<Category> childCategoryList, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN ProductImage pi ON p = pi.product WHERE p.company.id = :companyId")
    Page<Product> getProductPageWithCompanyId(@Param("companyId") Long companyId, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN ProductImage pi ON p = pi.product WHERE p.title LIKE %:keyword% AND p.rocketShipping = true")
    Page<Product> getProductPageBySearchWithRocket(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN ProductImage pi ON p = pi.product WHERE p.title LIKE %:keyword%")
    Page<Product> getProductPageBySearch(String keyword, Pageable pageable);
}
