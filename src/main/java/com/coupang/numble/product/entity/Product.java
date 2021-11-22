package com.coupang.numble.product.entity;

import java.util.HashSet;
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

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Product {

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
    private Set<ProductImage> thumbnailUrls = new HashSet<>();

    String title;
    Integer price;
    boolean goldBox;
    boolean rocketShipping;
    String thumbnailUrl;
    String detailsPageUrl;
}
