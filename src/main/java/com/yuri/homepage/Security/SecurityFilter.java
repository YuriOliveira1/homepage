package com.yuri.homepage.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yuri.homepage.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    // This class is use in my other Class (SecurityConfig)
    // This is a filter for my token and this filter occurs before the
    // UsernameAndPassword Filter

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       var token = this.recoverToken(request);
       if (token != null) {
        var username = tokenService.validateToken(token);
        UserDetails user = userRepository.findByUsername(username);

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
       }
       filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request) {
        var authReader = request.getHeader("Authorization");
        if (authReader == null) return null;
        return authReader.replace("Bearer","");
    }
}
