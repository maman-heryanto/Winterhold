package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

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
}
