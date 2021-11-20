package com.coupang.numble.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserReqDto {

    @Email(message = "Email형식이 올바르지 않습니다.")
    @NotBlank(message = "Email을 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String passwordCheck;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phoneNumber;
}
