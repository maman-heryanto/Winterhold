package com.winterhold.rest;

import com.winterhold.MapperHelper;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/author")
public class AuthorRestController {
    @Autowired
    AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name) { //Action Method
        try {
            Page<AuthorRowDto> hasilQuery = service.getAuthorTable(name, page);
            return ResponseEntity.status(200).body(hasilQuery);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get (@PathVariable(required = true) Long id){
        try {
            var headerDTO = service.getSingleAuthor(id);
            return ResponseEntity.status(HttpStatus.OK).body(headerDTO);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertAuthorDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var insertedEntity = service.saveAuthor(dto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertAuthorDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var updatedEntity = service.saveAuthor(dto);
                return ResponseEntity.status(200).body(updatedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id) {
        try {
            var totalDependent = service.totalDependentBook(id);
            if (totalDependent == 0) {
                service.deleteAuthor(id);
                //            return ResponseEntity.status(200).body(id);
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(500).body(totalDependent);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

}
