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

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT COUNT(P) FROM Product P")
    int countAll();

    @EntityGraph(attributePaths = {"type"}, type = EntityGraphType.LOAD)
    Optional<Product> findById(Long productId);

    @Query(value = "SELECT * FROM Product P WHERE P.company_id = ?1 and NOT P.id = ?2 LIMIT 4", nativeQuery = true)
    List<Product> findTop4ByCompanyIdAndNotId(Long companyId, Long productId);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.company.id = ?1")
    int countAllByCompanyId(Long companyId);

    @Query("SELECT COUNT(P) FROM Product P WHERE P.type in ?1")
    int countAllByCategories(List<Category> childCategoryIdList);
}
