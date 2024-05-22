package com.winterhold.rest;

import com.winterhold.MapperHelper;
import com.winterhold.dto.book.UpdateBookDto;
import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.dto.customer.CustomerRowDto;
import com.winterhold.dto.customer.UpdateCustomerDto;
import com.winterhold.dto.customer.UpsertCustomerDto;
import com.winterhold.service.CategoryService;
import com.winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
    @Autowired
    CustomerService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String membershipNumber) { //Action Method
        try {
            Page<CustomerRowDto> hasilQuery = service.getCustomerTable(membershipNumber, name, page);
            return ResponseEntity.status(200).body(hasilQuery);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get (@PathVariable(required = true) String membershipNumber){
        try {
            var headerDTO = service.getSingleCustomer(membershipNumber);
            return ResponseEntity.status(HttpStatus.OK).body(headerDTO);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCustomerDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var insertedEntity = service.saveCustomer(dto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PatchMapping("/{membershipNumber}")
    public ResponseEntity<Object> patch(@PathVariable(required = true) String membershipNumber){
        try {
            var updatedEntity = service.extendExpiredDate(membershipNumber);
            return ResponseEntity.status(200).body(updatedEntity);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCustomerDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var updateDto = new UpsertCustomerDto(
                        dto.getMembershipNumber(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getBirthDate(),
                        dto.getGender(),
                        dto.getPhone(),
                        dto.getAddress(),
                        dto.getMembershipExpireDate()
                );
                var updatedEntity = service.saveCustomer(updateDto);
                return ResponseEntity.status(200).body(updatedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{membershipNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String membershipNumber) {
        try {
            var totalDependent = service.totalDependentLoan(membershipNumber);
            if (totalDependent == 0) {
                service.deleteCustomer(membershipNumber);
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(500).body(totalDependent);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
