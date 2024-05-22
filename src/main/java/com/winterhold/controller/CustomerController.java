package com.winterhold.controller;

import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.dto.customer.UpdateCustomerDto;
import com.winterhold.dto.customer.UpsertCustomerDto;
import com.winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "") String membershipNumber,
                        @RequestParam(defaultValue = "false") Boolean extend,
                        @RequestParam(required = false) String selectedMember,
                        Model model){ //Action Method
        var hasilQuery = service.getCustomerTable(membershipNumber, name, page);
        var totalPages = hasilQuery.getTotalPages();

        //data yang dilempar ke html
        model.addAttribute("dataTable", hasilQuery);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        model.addAttribute("extend", extend);
        model.addAttribute("selectedMember", selectedMember );
        model.addAttribute("membershipNumber", membershipNumber);


        return "customer/customer-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String membershipNumber, Model model){
        var dto = new UpsertCustomerDto();
        dto.setMembershipExpireDate(LocalDate.now().plusYears(2));
        if (membershipNumber != null){
            dto = service.getSingleCustomer(membershipNumber);
            model.addAttribute("dto", dto);

            return "customer/update-customer-form";

        }

        model.addAttribute("dto", dto);

        return "customer/customer-form";
    }

    @PostMapping("/saveUpsert")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertCustomerDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "customer/customer-form";
        }

        service.saveCustomer(dto);
        return "redirect:/customer/index";
    }

    @PostMapping("/saveUpdate")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpdateCustomerDto dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "customer/update-customer-form";
        }

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

        service.saveCustomer(updateDto);
        return "redirect:/customer/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String membershipNumber, Model model){
        var totalDependent = service.totalDependentLoan(membershipNumber);

        if(totalDependent == 0){
            service.deleteCustomer(membershipNumber);
            return "redirect:/customer/index";
        }

        model.addAttribute("totalDependent", totalDependent);

        return "customer/delete-validation";
    }

    @GetMapping("/extendModal")
    public String upsertBookForm(@RequestParam(required = false) String membershipNumber, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("selectedMember", membershipNumber);
        redirectAttributes.addAttribute("extend", true);
        return "redirect:/customer/index";
    }

    @GetMapping("/extend")
    public String extend(@RequestParam(required = true) String membershipNumber){
        service.extendExpiredDate(membershipNumber);

        return "redirect:/customer/index";
    }

}
