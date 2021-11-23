package com.coupang.numble.order.entity;

import com.coupang.numble.common.entity.Base;
import com.coupang.numble.product.entity.ClothesOption;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.entity.ProductOption;
import com.coupang.numble.user.entity.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOption productOption;

    private String color;
    private String size;
    private int count;

    public void addCount(int add) {
        this.count += add;
    }
}
