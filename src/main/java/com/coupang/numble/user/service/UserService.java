package com.coupang.numble.user.service;

import com.coupang.numble.user.auth.Principal;
import com.coupang.numble.user.dto.PasswordChangeDto;
import com.coupang.numble.user.dto.UserReqDto;
import com.coupang.numble.user.entity.Authority;
import com.coupang.numble.user.entity.User;
import com.coupang.numble.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public boolean emailDuplicate(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public void createUser(UserReqDto signUpDto) {
        User user = modelMapper.map(signUpDto, User.class);
        Authority baseAuth = new Authority();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        baseAuth.setUser(user);
        baseAuth.setAuthority("ROLE_COMMON_USER");
        user.getAuthorities().add(baseAuth);
        repository.save(user);
    }

    public void changePhoneNumber(Principal principal, String phoneNumber) {
        User user = repository.findById(principal.getUser().getId())
            .orElseThrow(() -> new RuntimeException());
        user.setPhoneNumber(phoneNumber);
        repository.save(user);
        principal.setUser(user);
    }

    public void changeUsername(Principal principal, String username) {
        User user = repository.findById(principal.getUser().getId())
            .orElseThrow(() -> new RuntimeException());
        user.setUsername(username);
        repository.save(user);
        principal.setUser(user);
    }

    public void changeEmail(Principal principal, String email) {
        User user = repository.findById(principal.getUser().getId())
            .orElseThrow(() -> new RuntimeException());
        user.setEmail(email);
        repository.save(user);
        principal.setUser(user);
    }

    public void changePassword(Principal principal, PasswordChangeDto dto) {
        User user = repository.findById(principal.getUser().getId())
            .orElseThrow(() -> new RuntimeException());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        repository.save(user);
        principal.setUser(user);
    }
}
