package com.coupang.numble.common.controller;

import com.coupang.numble.product.service.ProductService;
import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(
        Model model,
        @AuthenticationPrincipal Principal principal,
        @PageableDefault(size = 60, sort = "id", direction = Direction.ASC) Pageable pageable
    ) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("productPage", productService.getAllProductPage(pageable));
        return "home";
    }
}