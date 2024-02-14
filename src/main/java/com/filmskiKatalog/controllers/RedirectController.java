package com.filmskiKatalog.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/profile")
    public String redirectToProfile(Authentication authentication) {
        if (authentication != null) {
            boolean isAdmin = false;
            boolean isKorisnik = false;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ADMIN")) {
                    isAdmin = true;
                    break; // Ako je korisnik admin, nema potrebe za daljnjom provjerom
                } else if (authority.getAuthority().equals("KORISNIK")) {
                    isKorisnik = true;
                    // Ne prekidamo petlju ovdje u slučaju da korisnik ima više uloga
                }
            }

            if (isAdmin) {
                return "redirect:/users";
            } else if (isKorisnik) {
                return "redirect:/korisnik/dashboard";
            }
        }
        return "redirect:/login"; // ili neka druga defaultna stranica za neprijavljene korisnike
    }
}
