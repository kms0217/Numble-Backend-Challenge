package com.coupang.numble.product.entity;

import com.coupang.numble.common.entity.Base;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "product")
    private Set<ProductOption> optionSet = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ClothesOption> clothesOptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImage> thumbnailUrls = new ArrayList<>();

    String title;
    Integer price;
    boolean goldBox;
    boolean rocketShipping;
    String detailsPageUrl;

}
