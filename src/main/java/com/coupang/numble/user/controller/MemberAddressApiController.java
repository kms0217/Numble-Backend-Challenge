package com.coupang.numble.user.controller;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.MemberAddressDto;
import com.coupang.numble.user.service.MemberAddressService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class MemberAddressApiController {

    private final MemberAddressService service;

    public MemberAddressApiController(MemberAddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MemberAddressDto>> getAllAddresses(
        @AuthenticationPrincipal Principal principal
    ) {
        List<MemberAddressDto> memberAddressDtos = service.getAllAddress(principal.getUser());
        return ResponseEntity.ok(memberAddressDtos);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<MemberAddressDto> getAddress(
        @AuthenticationPrincipal Principal principal,
        @PathVariable Long addressId
    ) {
        MemberAddressDto dto = service.getAddress(principal.getUser(), addressId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MemberAddressDto> createAddress(
        @AuthenticationPrincipal Principal principal,
        @Valid @RequestBody MemberAddressDto req,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }
        MemberAddressDto dto = service.createAddress(principal.getUser(), req);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<MemberAddressDto> updateAddress(
        @AuthenticationPrincipal Principal principal,
        @RequestBody MemberAddressDto req,
        @PathVariable Long addressId
    ) {
        MemberAddressDto dto = service.updateAddress(principal.getUser(), req, addressId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{addressId}")
    public HttpStatus deleteAddress(
        @AuthenticationPrincipal Principal principal,
        @PathVariable Long addressId
    ) {
        service.deleteAddress(principal.getUser(), addressId);
        return HttpStatus.NO_CONTENT;
    }
}
