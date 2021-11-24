package com.coupang.numble.review.repository;

import com.coupang.numble.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    int productReviewNum(@Param("productId") Long productId);

    @Query("SELECT AVG(r.star) FROM Review r WHERE r.product.id = :productId")
    int productStarRate(@Param("productId") Long productId);

}
