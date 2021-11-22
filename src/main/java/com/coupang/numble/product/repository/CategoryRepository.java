package com.coupang.numble.product.repository;

import com.coupang.numble.product.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"parent"})
    Optional<Category> findById(Long id);

    @EntityGraph(attributePaths = {"parent"})
    List<Category> findAllByParentId(Long categoryId);
}
