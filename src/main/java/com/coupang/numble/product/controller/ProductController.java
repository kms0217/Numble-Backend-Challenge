package com.coupang.numble.product.controller;

import com.coupang.numble.product.dto.ProductDetailDto;
import com.coupang.numble.product.dto.ProductDto;
import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.entity.Product;
import com.coupang.numble.product.service.CategoryService;
import com.coupang.numble.product.service.ProductService;
import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final CategoryService categoryService;

    public ProductController(ProductService service,
        CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("/{productId}")
    public String productDetailsFrom(
        @PathVariable Long productId,
        @AuthenticationPrincipal
        Principal principal,
        Model model
    ) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        ProductDetailDto product = service.getProduct(productId);
        List<Category> categoryList = categoryService.getAllParentCategory(product.getType().getId());
        List<Product> companyProduct = service.getCompanyProductLimit4(productId, product.getCompany().getId());
        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("companyProducts", companyProduct);
        return "productDetails";
    }

    @GetMapping("/categories/{categoryId}")
    public String categoryProductForm(
        @PathVariable Long categoryId,
        @AuthenticationPrincipal Principal principal,
        Model model,
        @PageableDefault(size = 60, sort = "id", direction = Direction.ASC) Pageable pageable
    ) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        List<Category> categoryList = categoryService.getAllParentCategory(categoryId);
        model.addAttribute("productPage", service.getCategoryProductPage(categoryId, pageable));
        model.addAttribute("categoryList", categoryList);
        return "productList";
    }

    @GetMapping("/companies/{companyId}")
    public String companyProductForm(
        @PathVariable Long companyId,
        @AuthenticationPrincipal Principal principal,
        Model model,
        @PageableDefault(size = 60, sort = "id", direction = Direction.ASC) Pageable pageable
    ) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("productPage", service.getAllCompanyProductPage(companyId, pageable));
        return "productList";
    }
}
