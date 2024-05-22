package com.winterhold.rest;

import com.winterhold.dto.account.JwtResponseDto;
import com.winterhold.dto.account.RequestJwtDto;
import com.winterhold.security.JwtManager;
import com.winterhold.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody RequestJwtDto dto){
        try{
            //Spring security memvalidasi uname & pass, (ini juga terjadi di mvc dibalik source code kita)
            var springSecurityToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(springSecurityToken);
            //kalau authenticationManager.authenticated gagal, akan langsung ke catch

            var userDetail = userDetailsService.loadUserByUsername(dto.getUsername());
            String jwtToken = jwtManager.generateToken(
                    dto.getUsername(),
                    dto.getSecretKey(),
                    dto.getSubject(),
                    dto.getAudience()
            );

            JwtResponseDto responseDTO = new JwtResponseDto(jwtToken, dto.getUsername());
            return  ResponseEntity.status(200).body(responseDTO);
        }catch (Exception exception){
            return ResponseEntity.status(401).body(exception.getMessage());
        }

    }
}
