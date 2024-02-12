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

    @GetMapping
    public String korisnikDashboard(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName(); // Dohvati email trenutno prijavljenog korisnika
            User korisnik = userRepository.findByEmail(email); // Pretpostavka da metoda vraća User objekat
            if (korisnik != null) {
                model.addAttribute("korisnik", korisnik);
            } else {
                // Ako korisnik nije pronađen, možete dodati logiku za obradu ove situacije
            }
        }
        return "korisnik/dashboard"; // Naziv Thymeleaf šablona za korisnički dashboard
    }

    // Ostale metode specifične za korisnika
}
