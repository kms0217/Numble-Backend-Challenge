package com.coupang.numble.user.service;

import com.coupang.numble.user.dto.UserDto;
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

    public void createUser(UserDto signUpDto) {
        User user = modelMapper.map(signUpDto, User.class);
        Authority baseAuth = new Authority();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        baseAuth.setAuthority("ROLE_COMMON_USER");
        user.getAuthorities().add(baseAuth);
        repository.save(user);
    }
}
