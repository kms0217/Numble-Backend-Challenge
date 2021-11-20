package com.coupang.numble.user.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserResDto;
import com.coupang.numble.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/membership")
public class MembershipController {

    private final UserService service;

    public MembershipController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String membershipForm(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        return "memberShip";
    }

    @PostMapping
    public String membershipToggle(@AuthenticationPrincipal Principal principal, Model model) {
        UserResDto userResDto = service.changeMembership(principal);
        model.addAttribute("user", userResDto);
        return "memberShip";
    }
}
