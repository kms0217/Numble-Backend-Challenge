package com.coupang.numble.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCoupangController {

    @GetMapping
    public String myCoupang() {
        return "redirect:/mc/orderList";
    }

    @GetMapping("/order/list")
    public String mcOrderList(Model model) {
        model.addAttribute("mcPage", "orderList");
        return "orderList";
    }

    @GetMapping("/coupon")
    public String mcCouponForm(Model model) {
        model.addAttribute("mcPage", "coupon");
        return "coupon";
    }
}
