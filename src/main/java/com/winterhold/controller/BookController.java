package com.winterhold.controller;

import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.dto.book.BookRowDto;
import com.winterhold.dto.book.UpdateBookDto;
import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.category.UpdateCategoryDto;
import com.winterhold.dto.category.UpsertCategoryDto;
import com.winterhold.service.AuthorService;
import com.winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService service;


    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){ //Action Method
        var hasilQuery = service.getCategoryTable(name, page);
        var totalPages = hasilQuery.getTotalPages();

        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);


        return "book/book-index";
    }

    @GetMapping("/upsertCategoryForm")
    public String upsertForm(@RequestParam(required = false) String categoryName, Model model){
        var dto = new UpsertCategoryDto();

        if (categoryName != null){
            dto = service.getSingleCategory(categoryName);
            model.addAttribute("dto", dto);
            return "book/update-category-form";
        }

        model.addAttribute("dto", dto);

        return "book/category-form";
    }

    @PostMapping("/saveUpsertCategory")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertCategoryDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "book/category-form";
        }

        service.saveCategory(dto);
        return "redirect:/book/index";
    }

    @PostMapping("/updateCategory")
    public String saveUpdate(@Valid @ModelAttribute("dto") UpdateCategoryDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "book/update-category-form";
        }
        var upsertDto = new UpsertCategoryDto(
          dto.getName(),
          dto.getFloor(),
          dto.getIsle(),
          dto.getBay()
        );

        service.saveCategory(upsertDto);
        return "redirect:/book/index";
    }


    @GetMapping("/category")
    public String detail(@RequestParam(required = true) String categoryName,
                         @RequestParam(defaultValue = "") String title,
                         @RequestParam(defaultValue = "") String author,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model) {

        Page<BookRowDto> hasilQuery = service.getBookByCategory(categoryName, title, author, page);
        long totalPages = hasilQuery.getTotalPages();

        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("currentPage", page);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("category", categoryName);
        model.addAttribute("totalPages", totalPages);
        return "book/category-detail";
    }

    @GetMapping("/upsertBookForm")
    public String upsertBookForm(@RequestParam(required = false) String code, @RequestParam(required = false) String categoryName, Model model){
        var dto = new UpsertBookDto();
        dto.setIsBorowwed(false);
        dto.setCategoryName(categoryName);

        if (code != null){
            dto = service.getSingleBook(code);
            model.addAttribute("authorDropdown", service.getAuthorDropdownList());
            model.addAttribute("dto", dto);

            return "book/update-book-form";
        }
        model.addAttribute("authorDropdown", service.getAuthorDropdownList());
        model.addAttribute("dto", dto);

        return "book/book-form";
    }

    @PostMapping("/updateBook")
    public String saveUpdate(@Valid @ModelAttribute("dto") UpdateBookDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("authorDropdown", service.getAuthorDropdownList());
            return "book/update-book-form";
        }
        var upsertDto = new UpsertBookDto(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorowwed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage()
        );

        service.saveBook(upsertDto);
        return "redirect:/book/index";
    }

    @PostMapping("/saveUpsertBook")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertBookDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "book/book-form";
        }

        service.saveBook(dto);
        return "redirect:/book/index";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(@RequestParam(required = true) String categoryName, Model model){
        var totalDependent = service.totalDependentBook(categoryName);

        if(totalDependent == 0){
            service.deleteCategory(categoryName);
            return "redirect:/book/index";
        }

        model.addAttribute("totalDependent", totalDependent);

        return "book/category-delete-validation";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam(required = true) String code, Model model, RedirectAttributes redirectAttributes){
        var totalDependent = service.totalDependentLoan(code);

        if(totalDependent == 0){
            redirectAttributes.addAttribute("categoryName", service.getSingleBook(code).getCategoryName());
            service.deleteBook(code);
            return "redirect:/book/category";
        }

        model.addAttribute("totalDependent", totalDependent);

        return "book/book-delete-validation";
    }
}
