package com.coupang.numble.product.dto;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.ClothesOption;
import com.coupang.numble.product.entity.Company;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.entity.ProductImage;
import com.coupang.numble.product.entity.ProductOption;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDto {

    private Long id;
    private Category type;
    private Company company;
    private Set<ProductOption> optionSet = new HashSet<>();
    private Set<ClothesOption> clothesOptions = new HashSet<>();
    private Set<ProductImage> thumbnailUrls = new HashSet<>();
    String title;
    Integer price;
    boolean goldBox;
    boolean rocketShipping;
    String detailsPageUrl;

    public static ProductDetailDto of(Product product) {
        return ModelMapperUtils.getModelMapper().map(product, ProductDetailDto.class);
    }
}
