package com.coupang.numble.common.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCoupangController {

    @GetMapping("/order/list")
    public String mcOrderList(Model model, @AuthenticationPrincipal Principal principal) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("mcPage", "orderList");
        return "mycoupang/order/orderList";
    }

    @GetMapping("/coupon")
    public String mcCouponForm(Model model, @AuthenticationPrincipal Principal principal) {
        if (principal != null)
            model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("mcPage", "coupon");
        return "mycoupang/coupon";
    }
}
