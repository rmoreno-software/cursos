package com.roger.curs.springboot.app.springboot_crud.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class RequiredValidation implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // return s != null && !s.isBlank();
        return StringUtils.hasText(s);
    }
}
