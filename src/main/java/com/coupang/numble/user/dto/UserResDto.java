package com.coupang.numble.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResDto {

    private String email;
    private String username;
    private String phoneNumber;
    private Boolean rocketWowMemberShip;
}
