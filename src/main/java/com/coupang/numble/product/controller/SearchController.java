package com.coupang.numble.product.controller;

import com.coupang.numble.product.dto.ProductDto;
import com.coupang.numble.product.service.ProductService;
import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ProductService service;

    public SearchController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String searchResultForm(
        @RequestParam("keyword") String keyword,
        @RequestParam(defaultValue = "false", name = "rocketFilter") boolean rocketFilter,
        @AuthenticationPrincipal Principal principal,
        Model model,
        @PageableDefault(size = 60, sort = "price", direction = Direction.ASC) Pageable pageable
    ) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        Page<ProductDto> productDtos = service.getProductPageBySearch(keyword, rocketFilter, pageable);
        model.addAttribute("productPage", productDtos);
        model.addAttribute("sort", pageable.getSort().stream().iterator().next().getProperty());
        model.addAttribute("direction", pageable.getSort().stream().iterator().next().getDirection());
        model.addAttribute("rocketFilter", rocketFilter);
        model.addAttribute("keyword", keyword);
        System.out.println(pageable.getSort().stream().iterator().next().getProperty());
        return "searchProduct";
    }
}