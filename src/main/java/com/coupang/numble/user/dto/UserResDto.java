package com.coupang.numble.user.dto;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResDto {

    private Long id;
    private String email;
    private String username;
    private String phoneNumber;
    private boolean rocketMembership;

    public static UserResDto of(User user) {
        return ModelMapperUtils.getModelMapper().map(user, UserResDto.class);
    }
}
