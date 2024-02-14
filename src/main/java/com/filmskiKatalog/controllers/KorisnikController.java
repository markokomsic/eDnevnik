package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.User;
import com.filmskiKatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/korisnik")
@PreAuthorize("hasAuthority('KORISNIK')")
public class KorisnikController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User korisnik = userRepository.findByEmail(email);
            if (korisnik != null) {
                model.addAttribute("korisnik", korisnik);
            } else {
                // Ako korisnik nije pronađen, preusmjerite na stranicu za prijavu ili prikažite poruku
                return "redirect:/login"; // Primjer preusmjeravanja na stranicu za prijavu
            }
        }
        return "korisnik/dashboard";
    }
}

