package com.coupang.numble.user.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.MemberAddressDto;
import com.coupang.numble.user.dto.UserResDto;
import com.coupang.numble.user.service.MemberAddressService;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class MemberAddressController {

    private final MemberAddressService service;

    public MemberAddressController(MemberAddressService service) {
        this.service = service;
    }

    @GetMapping
    public String addressManageForm(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("addressPage", "main");
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("addressList", service.getAllAddress(principal.getUser()));
        return "mycoupang/address/base";
    }

    @GetMapping("/add")
    public String addressAddFrom(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("addressPage", "add");
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("addressDto", new MemberAddressDto());
        return "mycoupang/address/base";
    }

    @PostMapping("/add")
    public String addressAdd(
        @AuthenticationPrincipal Principal principal,
        @ModelAttribute("addressDto") @Valid MemberAddressDto req,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addressPage", "add");
            return "mycoupang/address/base";
        }
        service.createAddress(principal.getUser(), req);
        return "redirect:/address";
    }

    @GetMapping("/edit/{addressId}")
    public String addressEditForm(
        @AuthenticationPrincipal Principal principal,
        Model model,
        @PathVariable Long addressId
    ) {
        model.addAttribute("addressPage", "edit");
        model.addAttribute("user", UserResDto.of(principal.getUser()));
        model.addAttribute("address", service.getAddress(principal.getUser(), addressId));
        return "mycoupang/address/base";
    }

    @PostMapping("/edit/{addressId}")
    public String addressEdit(
        @AuthenticationPrincipal Principal principal,
        Model model,
        @PathVariable Long addressId,
        @ModelAttribute("address") @Valid MemberAddressDto req,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            req.setId(addressId);
            model.addAttribute("addressPage", "edit");
            return "mycoupang/address/base";
        }
        service.updateAddress(principal.getUser(), req, addressId);
        return "redirect:/address";
    }
}
