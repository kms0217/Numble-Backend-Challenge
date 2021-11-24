package com.coupang.numble.order.repository;

import com.coupang.numble.order.entity.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByProductIdAndUserId(Long productId, Long userId);

    @EntityGraph(attributePaths = {"product", "productOption"})
    List<Cart> findByUserId(Long userId);

    void deleteByIdAndUserId(Long cartId, Long userId);

    @EntityGraph(attributePaths = {"product", "productOption"})
    List<Cart> findByUserIdAndSelected(Long userId, boolean select);
}
