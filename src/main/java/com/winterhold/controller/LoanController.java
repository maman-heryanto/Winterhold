package com.winterhold.controller;

import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.loan.UpsertLoanDto;
import com.winterhold.service.CustomerService;
import com.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String customerName,
                        @RequestParam(defaultValue = "") String bookTitle,
                        @RequestParam(defaultValue = "false") Boolean insert,
                        @RequestParam(defaultValue = "false") Boolean detail,
                        @RequestParam(defaultValue = "false") Boolean returnBook,
                        @RequestParam (required = false)Long id,
                        @RequestParam (required = false)Long selectedLoan,
                        Model model){ //Action Method
        var hasilQuery = service.getLoanTable(bookTitle, customerName, page);
        var totalPages = hasilQuery.getTotalPages();
        if(insert){
            var dto = new UpsertLoanDto();

            if (id != null){
                dto = service.getSingleLoan(id);
                model.addAttribute("selectedBook", service.getSelectedBookTitle(dto.getBookCode()));
                model.addAttribute("selectedMember", service.getSelectedMemberName(dto.getCustomerNumber()));
            }
            model.addAttribute("bookDropdown", service.getBookDropdownList());
            model.addAttribute("customerDropdown", service.getCustomerDropdownList());
            model.addAttribute("dto", dto);

        }
        if(detail){
            var dataDetail = service.getLoanDetail(id);
            model.addAttribute("data", dataDetail);

        }
        //data yang dilempar ke html
        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("customerName", customerName);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("upsert", insert);
        model.addAttribute("detail", detail);
        model.addAttribute("returnBook", returnBook);
        model.addAttribute("selectedLoan", selectedLoan);

        return "loan/loan-index";
    }

    @GetMapping("/upsertForm")
    public String upsertBookForm(@RequestParam(required = false) Long id, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("insert", true);
        return "redirect:/loan/index";
    }

    @GetMapping("/returnModal")
    public String returnModal(@RequestParam(required = false) Long id, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("returnBook", true);
        return "redirect:/loan/index";
    }

    @PostMapping("/saveUpsert")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertLoanDto dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("bookDropdown", service.getBookDropdownList());
            model.addAttribute("customerDropdown", service.getCustomerDropdownList());
            return "redirect:/loan/index";
        }

        service.saveLoan(dto);
        return "redirect:/loan/index";
    }

    @GetMapping("/detail")
    public String detail(Long id, Model model, RedirectAttributes redirectAttributes){ //Action Method
//        var hasilQuery = service.getLoanDetail(id);
//        model.addAttribute("data", hasilQuery);

        redirectAttributes.addAttribute("selectedLoan", id);
        redirectAttributes.addAttribute("detail", true);

        return "redirect:/loan/index";
    }

    @GetMapping("/return")
    public String returnBook(Long id){ //Action Method
        service.returnBook(id);

        return "redirect:/loan/index";
    }
}
