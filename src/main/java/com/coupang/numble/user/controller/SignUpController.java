package com.coupang.numble.user.controller;

import com.coupang.numble.user.dto.UserReqDto;
import com.coupang.numble.user.service.UserService;
import com.coupang.numble.user.validator.SignUpValidator;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;
    private final SignUpValidator validator;

    public SignUpController(UserService userService,
        SignUpValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping
    public String signupFrom(Model model) {
        model.addAttribute("user", new UserReqDto());
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("user") @Valid UserReqDto userDto, BindingResult bindingResult) {
        validator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.createUser(userDto);
        return "redirect:/login";
    }

}
