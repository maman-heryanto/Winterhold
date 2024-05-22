package com.winterhold.controller;

import com.winterhold.dto.account.RegisterDto;
import com.winterhold.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping("/loginForm")
    public  String loginForm(){
        return "/account/login-form";
    }

    @GetMapping("/registerForm")
    public String RegisterForm(Model model){
        var dto = new RegisterDto();
        model.addAttribute("dto", dto);
        return "/account/register-form";
    }

    @PostMapping("/submitRegister")
    public String submitRegister(@Valid @ModelAttribute("dto") RegisterDto dto,
                                 BindingResult bindingResult,
                                 Model model){
        if(bindingResult.hasErrors()){
            return "/account/register-form";
        }
        service.registerAccount(dto);
        return "redirect:/account/loginForm";
    }
}
