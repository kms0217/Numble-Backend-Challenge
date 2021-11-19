package com.coupang.numble.user.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.UserReqDto;
import com.coupang.numble.user.dto.UserResDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginFrom() {
        return "login";
    }

    @PostMapping("/confirmPassword")
    public ResponseEntity confirmPassword(
        @AuthenticationPrincipal Principal principal,
        @RequestBody UserReqDto dto,
        ModelMapper modelMapper
    ) {
        if (new BCryptPasswordEncoder().matches(dto.getPassword(), principal.getPassword())) {
            return ResponseEntity.status(200).body(modelMapper.map(principal.getUser(), UserResDto.class));
        }
        return ResponseEntity.badRequest().body(null);
    }
}
