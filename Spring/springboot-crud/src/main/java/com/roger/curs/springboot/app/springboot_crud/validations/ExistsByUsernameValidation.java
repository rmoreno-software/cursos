package com.roger.curs.springboot.app.springboot_crud.validations;

import com.roger.curs.springboot.app.springboot_crud.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userService == null) return true;
        if (s == null || s.isBlank()) return true;
        return userService.findByUsername(s).isEmpty();
    }
}
