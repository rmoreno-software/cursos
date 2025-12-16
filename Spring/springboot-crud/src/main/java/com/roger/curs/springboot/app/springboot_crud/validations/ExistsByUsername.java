package com.roger.curs.springboot.app.springboot_crud.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByUsernameValidation.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {

    String message() default "{ExistsByUsername.user.username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
