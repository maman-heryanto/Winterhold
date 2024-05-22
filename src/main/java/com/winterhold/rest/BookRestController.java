package com.winterhold.rest;

import com.winterhold.MapperHelper;
import com.winterhold.dto.book.BookRowDto;
import com.winterhold.dto.book.UpdateBookDto;
import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    BookService service;

    @GetMapping("category/{categoryName}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String categoryName,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String title,
                                      @RequestParam(defaultValue = "") String author) { //Action Method
        try {
            Page<BookRowDto> hasilQuery = service.getBookByCategory(categoryName, title, author, page);
            return ResponseEntity.status(200).body(hasilQuery);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertBookDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var insertedEntity = service.saveBook(dto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpdateBookDto dto, BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                var updateDto = new UpsertBookDto(
                        dto.getCode(),
                        dto.getTitle(),
                        dto.getCategoryName(),
                        dto.getAuthorId(),
                        dto.getIsBorowwed(),
                        dto.getSummary(),
                        dto.getReleaseDate(),
                        dto.getTotalPage()
                );
                var insertedEntity = service.saveBook(updateDto);
                return ResponseEntity.status(201).body(insertedEntity);
            }
            return ResponseEntity.status(422).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{bookCode}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String bookCode) {
        try {
            var totalDependent = service.totalDependentLoan(bookCode);
            if (totalDependent == 0) {
                service.deleteBook(bookCode);
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(500).body(totalDependent);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }


}
