package com.winterhold.rest;

import com.winterhold.MapperHelper;
import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.customer.CustomerRowDto;
import com.winterhold.dto.loan.LoanRowDto;
import com.winterhold.dto.loan.UpsertLoanDto;
import com.winterhold.service.CustomerService;
import com.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/loan")
public class LoanRestController{
    @Autowired
    LoanService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String booktTitle,
                                      @RequestParam(defaultValue = "") String customerName) { //Action Method
        try {
            Page<LoanRowDto> hasilQuery = service.getLoanTable(booktTitle, customerName, page);
            return ResponseEntity.status(200).body(hasilQuery);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertLoanDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var insertedEntity = service.saveLoan(dto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@PathVariable(required = true) Long id){
        try {
            if (service.getSingleLoan(id).getReturnDate()!=null){
                var updatedEntity = service.returnBook(id);
                return ResponseEntity.status(200).body(updatedEntity);
            }
            return ResponseEntity.status(400).body("Sudah melakukan Pengembalian");
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get (@PathVariable(required = true) Long id){
        try {
            var headerDTO = service.getLoanDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body(headerDTO);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

}
