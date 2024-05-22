package com.winterhold.controller;

import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService service;


    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){ //Action Method
        var hasilQuery = service.getAuthorTable(name, page);
        var totalPages = hasilQuery.getTotalPages();

        //data yang dilempar ke html
        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);


        return "author/author-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id, Model model){
        var dto = new UpsertAuthorDto();

        if (id != null){
            dto = service.getSingleAuthor(id);
        }

        model.addAttribute("dto", dto);

        return "author/author-form";
    }

    @PostMapping("/saveUpsert")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertAuthorDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "author/author-form";
        }

        service.saveAuthor(dto);
        return "redirect:/author/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model){
        var totalDependent = service.totalDependentBook(id);

        if(totalDependent == 0){
            service.deleteAuthor(id);
            return "redirect:/author/index";
        }

        model.addAttribute("totalDependent", totalDependent);

        return "author/delete-validation";
    }

    @GetMapping("/book")
    public String detail(Long id,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){ //Action Method
        var hasilQuery = service.getBookByAuthor(id, page);
        var totalPages = hasilQuery.getTotalPages();
        var header = service.getAuthorHeader(id);

        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("header", header);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "author/author-book";
    }
}
