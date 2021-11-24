package com.coupang.numble.product.dto;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    Long id;
    private
    String title;
    Integer price;
    boolean rocketShipping;
    String thumbnailUrl;

    public static ProductDto of(Product product) {
        ProductDto productDto = ModelMapperUtils.getModelMapper().map(product, ProductDto.class);
        productDto.thumbnailUrl = product.getThumbnailUrls().get(0).getThumbnailUrl();
        return productDto;
    }
}
