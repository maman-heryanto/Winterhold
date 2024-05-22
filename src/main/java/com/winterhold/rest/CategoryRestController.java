package com.winterhold.rest;

import com.winterhold.MapperHelper;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.dto.category.UpdateCategoryDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.service.AuthorService;
import com.winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name) { //Action Method
        try {
            Page<CategoryRowDto> hasilQuery = service.getCategoryTable(name, page);
            return ResponseEntity.status(200).body(hasilQuery);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get (@PathVariable(required = true) String name){
        try {
            var headerDTO = service.getSingleCategory(name);
            return ResponseEntity.status(HttpStatus.OK).body(headerDTO);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var insertedEntity = service.saveCategory(dto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCategoryDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var upsertDto = new UpsertCategoryDto(
                        dto.getName(),
                        dto.getFloor(),
                        dto.getIsle(),
                        dto.getBay()
                );
                var updatedEntity = service.saveCategory(upsertDto);
                return ResponseEntity.status(200).body(updatedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{categeoryName}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String categeoryName) {
        try {
            var totalDependent = service.totalDependentBook(categeoryName);
            if (totalDependent == 0) {
                service.deleteCategory(categeoryName);
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(500).body(totalDependent);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

}
