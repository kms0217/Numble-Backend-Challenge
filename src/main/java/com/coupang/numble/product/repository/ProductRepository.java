package com.coupang.numble.product.repository;

import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.Product;
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

    @Query("SELECT COUNT(P) FROM Product P WHERE P.company.id = :companyId")
    int countAllByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.type in :categoryList")
    int countAllByCategories(@Param("categoryList") List<Category> childCategoryIdList);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.title LIKE %:keyword%")
    int countAllByFilter(@Param("keyword") String keyword);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.title LIKE %:keyword% AND P.rocketShipping = true")
    int countAllByFilterWithRocket(@Param("keyword") String keyword);

}
