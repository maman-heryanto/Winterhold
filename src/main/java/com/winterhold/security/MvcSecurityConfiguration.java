package com.winterhold.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity//.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/resources/**", "/account/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .loginPage("/account/loginForm")
                        .loginProcessingUrl("/submitLogin")//ini url ke action Post Login
                ).logout((logout) -> logout.permitAll()
                );

        return httpSecurity.build();
    }
}
