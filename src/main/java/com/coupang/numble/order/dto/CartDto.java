package com.coupang.numble.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {

    private Long id;

    @NotNull(message = "장바구니에 담을 상품 Id는 필수입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 1개의 상품이 선택되어야 합니다.")
    private int count;

    private Long optionId;
    private String size;
    private String color;

}