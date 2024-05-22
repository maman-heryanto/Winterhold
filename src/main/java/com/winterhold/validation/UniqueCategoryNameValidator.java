package com.winterhold.validation;

import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.service.BookService;
import com.winterhold.service.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, UpsertCategoryDto> {
    @Autowired
    BookService service;

    @Override
    public boolean isValid(UpsertCategoryDto value, ConstraintValidatorContext context){
        String name = value.getName();
        var isValid = service.isCategoryNameValid(name);

        return isValid;
    }

}
