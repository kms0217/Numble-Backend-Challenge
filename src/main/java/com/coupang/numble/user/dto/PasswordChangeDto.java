package com.coupang.numble.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PasswordChangeDto {

    private String originPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
