package com.coupang.numble.user.validator;

import com.coupang.numble.user.dto.UserReqDto;
import com.coupang.numble.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SignUpValidator implements Validator {

    private final UserService userService;

    public SignUpValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserReqDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserReqDto target = (UserReqDto) obj;
        if (target.getPassword() == null ||
            !target.getPassword().equals(target.getPasswordCheck())) {
            errors.rejectValue("passwordCheck", "key", "비밀번호가 일치하지 않습니다.");
        }
        if (userService.emailDuplicate(target.getEmail())) {
            errors.rejectValue("email", "key", "이미 존재하는 Email입니다.");
        }
    }
}
