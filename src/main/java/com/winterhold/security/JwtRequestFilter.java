package com.winterhold.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null){
            token = authorizationHeader.replace("Bearer ", "");
            username = jwtManager.getUsernameByToken(token);
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(username != null && authentication == null){
            var userDeatils =  userDetailsService.loadUserByUsername(username);

            Boolean isTokenValid = jwtManager.validateToken(token, userDeatils);

            if(isTokenValid){
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDeatils,
                        null,
                        userDeatils.getAuthorities());
                var authenticationDetailSource = new WebAuthenticationDetailsSource().buildDetails(request);
                authenticationToken.setDetails(authenticationDetailSource);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
