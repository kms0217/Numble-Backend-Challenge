package com.coupang.numble.order.dto;

import com.coupang.numble.product.entity.Product;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private Product product;
    private LocalDateTime arriveDate;
    private boolean show;
    private boolean arrive;
    private int count;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
