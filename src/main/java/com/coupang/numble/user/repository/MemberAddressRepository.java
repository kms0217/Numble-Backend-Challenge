package com.coupang.numble.user.repository;

import com.coupang.numble.user.entity.MemberAddress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

    List<MemberAddress> findAllByUserIdOrderByMainDesc(Long usrId);
    Optional<MemberAddress> findByIdAndUserId(Long addressId, Long userId);
    void deleteByIdAndUserId(Long addressId, Long userId);
    Optional<MemberAddress> findByUserIdAndMain(Long userId, boolean main);
}
