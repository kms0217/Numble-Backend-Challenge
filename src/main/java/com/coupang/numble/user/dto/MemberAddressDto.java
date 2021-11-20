package com.coupang.numble.user.dto;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.user.entity.MemberAddress;
import com.coupang.numble.user.entity.MemberAddress.Place;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MemberAddressDto {

    private Long id;

    @NotBlank(message = "받는사람 이름을 입력해주세요.")
    private String receiverName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String telephone;
    private String homePhone;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
    private boolean main;

    @NotNull(message = "장소를 선택해주세요.")
    private Place place;

    public static MemberAddressDto of(MemberAddress memberAddress) {
        return ModelMapperUtils.getModelMapper().map(memberAddress, MemberAddressDto.class);
    }
}
