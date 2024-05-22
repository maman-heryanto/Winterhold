package com.winterhold.validation;

import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.service.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueBookCodeValidator implements ConstraintValidator<UniqueBookCode, UpsertBookDto> {
    @Autowired
    BookService service;

    @Override
    public boolean isValid(UpsertBookDto value, ConstraintValidatorContext context){
        String name = value.getCode();
        var isValid = service.isBookCodeValid(name);

        return isValid;
    }
}
