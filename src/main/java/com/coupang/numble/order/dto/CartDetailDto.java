package com.coupang.numble.order.dto;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.order.entity.Cart;
import com.coupang.numble.product.entity.ProductOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDetailDto {

    private Long cartId;
    private Long productId;
    private String productName;
    private int price;
    private int count;
    private String optionStr;
    private boolean rocketShipping;
    private LocalDateTime arriveExpected;
    private boolean goldBox;
    private boolean selected;

    public static CartDetailDto of(Cart cart) {
        CartDetailDto cartDetailDto = ModelMapperUtils.getModelMapper().map(cart, CartDetailDto.class);
        cartDetailDto.setCartId(cart.getId());
        cartDetailDto.setProductId(cart.getProduct().getId());
        cartDetailDto.setProductName(cart.getProduct().getTitle());
        cartDetailDto.setRocketShipping(cart.getProduct().isRocketShipping());
        cartDetailDto.setPrice(cart.getProduct().getPrice());
        cartDetailDto.setGoldBox(cart.getProduct().isGoldBox());
        ProductOption option = cart.getProductOption();
        if (option != null) {
            cartDetailDto.setPrice(option.getPrice());
            cartDetailDto.setOptionStr(option.getTitle());
        } else {
            cartDetailDto.setOptionStr(cart.getColor() + " " + cart.getSize());
        }
        if (cartDetailDto.isRocketShipping()) {
            cartDetailDto.setArriveExpected(LocalDateTime.now().withHour(6).withMinute(0).withNano(0).withSecond(0));
        } else {
            cartDetailDto.setArriveExpected(LocalDateTime.now().plusDays(3));
        }
        return cartDetailDto;
    }
}
