package com.coupang.numble.user.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.PasswordChangeDto;
import com.coupang.numble.user.dto.UserReqDto;
import com.coupang.numble.user.service.UserService;
import com.coupang.numble.user.validator.SignUpValidator;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;
    private final SignUpValidator validator;

    public UserController(UserService userService,
        SignUpValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/userModify")
    public String userModifyForm(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("user", principal.getUser());
        return "userModify";
    }

    @PutMapping("/user/email")
    @ResponseBody
    public HttpStatus changeEmail(
        @AuthenticationPrincipal Principal principal,
        @RequestBody UserReqDto dto
    ) {
        if (userService.emailDuplicate(dto.getEmail()))
            throw new RuntimeException();
        userService.changeEmail(principal, dto.getEmail());
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/user/phoneNumber")
    @ResponseBody
    public HttpStatus changePhoneNumber(
        @AuthenticationPrincipal Principal principal,
        @RequestBody UserReqDto dto
    ) {
        userService.changePhoneNumber(principal, dto.getPhoneNumber());
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/user/username")
    @ResponseBody
    public HttpStatus changeUsername(
        @AuthenticationPrincipal Principal principal,
        @RequestBody UserReqDto dto
    ) {
        userService.changeUsername(principal, dto.getUsername());
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/user/password")
    @ResponseBody
    public HttpStatus changePassword(
        @AuthenticationPrincipal Principal principal,
        @RequestBody PasswordChangeDto dto
    ) {
        if (!new BCryptPasswordEncoder().matches(principal.getPassword(), dto.getOriginPassword()))
            throw new RuntimeException();
        if (dto.getNewPassword() == null || dto.getNewPassword().equals(dto.getNewPasswordConfirm()))
            throw new RuntimeException();
        userService.changePassword(principal, dto);
        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("/user/me")
    @ResponseBody
    public HttpStatus deleteUser(@AuthenticationPrincipal Principal principal, HttpSession session) {
        userService.deleteUser(principal, session);
        return HttpStatus.NO_CONTENT;
    }
}
