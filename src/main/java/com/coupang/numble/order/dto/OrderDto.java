package com.coupang.numble.order.dto;

import com.coupang.numble.user.entity.MemberAddress;
import com.coupang.numble.user.entity.MemberAddress.Place;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Set<OrderDetailsDto> orderDetailsDots = new HashSet<>();
    private int totalPrice;
    private String buyerPhoneNumber;
    private Place place;
    private MemberAddress address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
