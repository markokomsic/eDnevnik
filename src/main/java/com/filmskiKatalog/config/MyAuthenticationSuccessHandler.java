package com.filmskiKatalog.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var authorities = authentication.getAuthorities();

        // Logika preusmjeravanja na osnovu uloge
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/users");
        }  else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("KORISNIK"))) {
            response.sendRedirect("/filmovi/home");
        } else {
            response.sendRedirect("/login");
        }
    }
}