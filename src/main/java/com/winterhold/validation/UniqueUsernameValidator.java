package com.winterhold.validation;

import com.winterhold.service.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    AccountService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return service.isUsernameValid(value);
    }
}
