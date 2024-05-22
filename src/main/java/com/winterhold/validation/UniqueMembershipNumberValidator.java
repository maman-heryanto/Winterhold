package com.winterhold.validation;

import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.dto.customer.UpsertCustomerDto;
import com.winterhold.service.BookService;
import com.winterhold.service.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueMembershipNumberValidator implements ConstraintValidator<UniqueMembershipNumber, UpsertCustomerDto> {
    @Autowired
    CustomerService service;

    @Override
    public boolean isValid(UpsertCustomerDto value, ConstraintValidatorContext context){
        String membershipNumber = value.getMembershipNumber();
        var isValid = service.isMembershipNumberValid(membershipNumber);

        return isValid;
    }
}
