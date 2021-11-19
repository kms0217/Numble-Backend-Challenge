package com.coupang.numble.common.controller;

import com.coupang.numble.user.auth.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mc")
public class MyCoupangController {

    @GetMapping
    public String myCoupang() {
        return "redirect:/mc/orderList";
    }

    @GetMapping("/orderList")
    public String mcOrderList(Model model) {
        model.addAttribute("mcPage", "orderList");
        return "mycoupang";
    }

    @GetMapping("/membership")
    public String mcMembershipForm(Model model) {
        model.addAttribute("mcPage", "membership");
        return "mycoupang";
    }

    @GetMapping("/coupon")
    public String mcCouponForm(Model model) {
        model.addAttribute("mcPage", "coupon");
        return "mycoupang";
    }

    @GetMapping("/address")
    public String mcAddressForm(Model model) {
        model.addAttribute("mcPage", "address");
        return "mycoupang";
    }
}
