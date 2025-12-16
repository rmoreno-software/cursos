package com.roger.curs.springboot.app.springboot_crud.validations;

import com.roger.curs.springboot.app.springboot_crud.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "Mensaje de error 1");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null, "Mensaje de error 2");

        if(product.getPrice() == null || product.getPrice() < 20) {
            errors.rejectValue("price", null, "Mensaje de error 3");
        }
    }
}
