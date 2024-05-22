package com.winterhold.validation;

import com.winterhold.dto.account.RegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComparePasswordValidator implements ConstraintValidator<ComparePassword, RegisterDto> {
    @Override
    public boolean isValid(RegisterDto value, ConstraintValidatorContext context){
        return value.getPassword().equals(value.getPasswordConfirmation());
    }
}
