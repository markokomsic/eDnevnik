package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Recenzija;
import com.filmskiKatalog.models.User;
import com.filmskiKatalog.repositories.RecenzijaRepository;
import com.filmskiKatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recenzije")
public class RecenzijaController {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addRecenzija(@RequestBody Recenzija recenzija, Authentication authentication) {
        String email = authentication.getName();
        User korisnik = userRepository.findByEmail(email);
        if (korisnik == null) {
            return new ResponseEntity<>("Korisnik nije pronađen", HttpStatus.NOT_FOUND);
        }
        recenzija.setUser(korisnik);
        Recenzija savedRecenzija = recenzijaRepository.save(recenzija);
        return new ResponseEntity<>(savedRecenzija, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecenzija(@PathVariable Long id, Authentication authentication) {
        Recenzija recenzija = recenzijaRepository.findById(id).orElse(null);
        if (recenzija == null) {
            return new ResponseEntity<>("Recenzija nije pronađena", HttpStatus.NOT_FOUND);
        }

        String email = authentication.getName();
        User korisnik = userRepository.findByEmail(email);

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!recenzija.getUser().equals(korisnik) && !isAdmin) {
            return new ResponseEntity<>("Nemate pravo brisati ovu recenziju", HttpStatus.FORBIDDEN);
        }

        recenzijaRepository.delete(recenzija);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Dodajte dodatne metode prema potrebi
}
