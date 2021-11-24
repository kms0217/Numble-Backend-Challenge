package com.coupang.numble.order.dto;

import com.coupang.numble.user.entity.MemberAddress.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderReqDto {

    private Long productId;
    private Long addressId;
    private int count;
    private String phoneNumber;
    private Place place;
}
