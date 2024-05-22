package com.winterhold.service;

import com.winterhold.dao.AccountRepository;
import com.winterhold.dto.account.RegisterDto;
import com.winterhold.entity.Account;
import com.winterhold.security.AplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;





    public Boolean isUsernameValid(String username){
        return accountRepository.count(username) <= 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = accountRepository.findById(username).get();

        return new AplicationUserDetails(entity);
    }

    public void registerAccount(RegisterDto dto){
        var entity = new Account(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())
        );

        accountRepository.save(entity);
    }
}
