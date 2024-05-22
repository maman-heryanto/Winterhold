package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.DropdownDTO;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.dto.book.BookRowDto;
import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.entity.Author;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import com.winterhold.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    LoanRepository loanRepository;

    private final int rowsPerPage = 10;
    public Page<CategoryRowDto> getCategoryTable(String name, Integer page){
        var pageable = PageRequest.of(page - 1, rowsPerPage, Sort.by("id"));
        return categoryRepository.getCategoryTable(name, pageable);
    }

    public Category saveCategory(UpsertCategoryDto dto){
        var entity = new Category(
                dto.getName(),
                dto.getFloor(),
                dto.getIsle(),
                dto.getBay()

        );

        return categoryRepository.save(entity);
    }

    public UpsertCategoryDto getSingleCategory(String name){
        var selectedEntity = categoryRepository.findById(name).get();
        var dto = new UpsertCategoryDto(
                selectedEntity.getName(),
                selectedEntity.getFloor(),
                selectedEntity.getIsle(),
                selectedEntity.getBay()
        );
        return dto;
    }

    public void deleteCategory(String name){
        categoryRepository.deleteById(name);
    }

    public Long totalDependentBook(String name){
        return bookRepository.countByCategoryName(name);
    }

    public Long totalDependentLoan(String code){
        return loanRepository.countByBookCode(code);
    }

    public Page<BookRowDto> getBookByCategory(String categoryName, String title, String author, Integer page){
        var pageable = PageRequest.of(page - 1, rowsPerPage, Sort.by("id"));
        var hasil = bookRepository.getBookByCategory(categoryName, title, author, pageable);
        return hasil;
    }

    public Book saveBook(UpsertBookDto dto){
        var entity = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorowwed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage()
        );

        return bookRepository.save(entity);
    }

    public UpsertBookDto getSingleBook(String code){
        var selectedEntity = bookRepository.findById(code).get();
        var dto = new UpsertBookDto(
                selectedEntity.getCode(),
                selectedEntity.getTitle(),
                selectedEntity.getCategoryName(),
                selectedEntity.getAuthorId(),
                selectedEntity.getIsBorrowed(),
                selectedEntity.getSummary(),
                selectedEntity.getReleaseDate(),
                selectedEntity.getTotalPage()
        );
        return dto;
    }

    public void deleteBook(String code){
        bookRepository.deleteById(code);
    }

    public List<DropdownDTO> getAuthorDropdownList(){
        return authorRepository.getDropdownList();
    }

    public Boolean isCategoryNameValid(String name){
        var foundEntity = categoryRepository.findById(name);
        return foundEntity.isEmpty();
    }

    public Boolean isBookCodeValid(String code){
        var foundEntity = bookRepository.findById(code);
        return foundEntity.isEmpty();
    }

}
