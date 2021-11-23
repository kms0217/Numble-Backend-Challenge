package com.coupang.numble.order.service;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.order.dto.CartDetailDto;
import com.coupang.numble.order.dto.CartDto;
import com.coupang.numble.order.entity.Cart;
import com.coupang.numble.order.repository.CartRepository;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.entity.ProductOption;
import com.coupang.numble.product.repository.ProductOptionRepository;
import com.coupang.numble.product.repository.ProductRepository;
import com.coupang.numble.user.entity.User;
import com.coupang.numble.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductOptionRepository productOptionRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
        UserRepository userRepository,
        ProductOptionRepository productOptionRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Transactional
    public Long addCart(CartDto req, Long userId) {
        Product product = productRepository.getById(req.getProductId());
        User user = userRepository.findById(userId).orElse(null);
        Cart savedCart = cartRepository.findByProductIdAndUserId(product.getId(), userId);
        if (savedCart != null) {
            savedCart.addCount(req.getCount());
            return cartRepository.save(savedCart).getId();
        }
        Cart cart = ModelMapperUtils.getModelMapper().map(req, Cart.class);
        cart.setProduct(product);
        cart.setUser(user);
        if (req.getOptionId() != null){
            ProductOption option =
                productOptionRepository.findById(req.getOptionId()).orElseThrow(RuntimeException::new);
            cart.setProductOption(option);
            return cartRepository.save(cart).getId();
        }
        return cartRepository.save(cart).getId();
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartItems(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream().map(CartDetailDto::of).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCart(Long userId, Long cartId) {
        cartRepository.deleteByIdAndUserId(cartId, userId);
    }
}
