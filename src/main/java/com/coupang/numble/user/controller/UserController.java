package com.coupang.numble.user.controller;

import com.coupang.numble.user.dto.UserDto;
import com.coupang.numble.user.service.UserService;
import com.coupang.numble.user.validator.SignUpValidator;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final SignUpValidator validator;

    public UserController(UserService userService,
        SignUpValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/login")
    public String loginFrom() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupFrom(Model model) {
        model.addAttribute("user", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.createUser(userDto);
        return "redirect:/";
    }
}
