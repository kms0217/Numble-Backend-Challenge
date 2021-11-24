package com.coupang.numble.user.service;

import com.coupang.numble.common.utils.ModelMapperUtils;
import com.coupang.numble.user.dto.MemberAddressDto;
import com.coupang.numble.user.entity.MemberAddress;
import com.coupang.numble.user.entity.User;
import com.coupang.numble.user.repository.MemberAddressRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberAddressService {

    private final MemberAddressRepository repository;

    public MemberAddressService(MemberAddressRepository repository) {
        this.repository = repository;
    }

    public List<MemberAddressDto> getAllAddress(User user) {
        List<MemberAddress> deliveries = repository.findAllByUserIdOrderByMainDesc(user.getId());
        return deliveries.stream().map(MemberAddressDto::of).collect(Collectors.toList());
    }

    public MemberAddressDto getAddress(User user, Long addressId) {
        MemberAddress memberAddress = repository.findByIdAndUserId(addressId, user.getId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 주소"));
        return MemberAddressDto.of(memberAddress);
    }

    public MemberAddressDto createAddress(User user, MemberAddressDto req) {
        MemberAddress memberaddress = ModelMapperUtils.getModelMapper()
            .map(req, MemberAddress.class);
        memberaddress.setUser(user);
        MemberAddress saved = repository.save(memberaddress);
        return MemberAddressDto.of(saved);
    }

    public MemberAddressDto updateAddress(User user, MemberAddressDto req, Long addressId) {
        ModelMapper mapper = ModelMapperUtils.getModelMapper();
        MemberAddress memberAddress = repository.findByIdAndUserId(addressId, user.getId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 주소"));
        MemberAddress newMemberAddress = mapper.map(req, MemberAddress.class);
        mapper.map(newMemberAddress, memberAddress);
        if (memberAddress.isMain()) {
            defaultAddressChange(user);
        }
        return MemberAddressDto.of(repository.save(memberAddress));
    }

    public void defaultAddressChange(User user) {
        MemberAddress memberAddress = repository.findByUserIdAndMain(user.getId(), true)
            .orElse(null);
        if (memberAddress == null) {
            return;
        }
        memberAddress.setMain(false);
        repository.save(memberAddress);
    }

    public MemberAddressDto getMainAddress(User user) {
        MemberAddress memberAddress = repository.findByUserIdAndMain(user.getId(), true)
            .orElse(null);
        if (memberAddress == null)
            return null;
        return MemberAddressDto.of(memberAddress);
    }

    public void deleteAddress(User user, Long addressId) {
        repository.deleteByIdAndUserId(addressId, user.getId());
    }
}
