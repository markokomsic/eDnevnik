package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Recenzija;
import com.filmskiKatalog.models.User;
import com.filmskiKatalog.repositories.RecenzijaRepository;
import com.filmskiKatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recenzija")
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

    @PostMapping("/delete/{id}")
    public String deleteRecenzija(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        Recenzija recenzija = recenzijaRepository.findById(id).orElse(null);
        Long filmIdd = recenzija.getFilm().getId();
        if (recenzija == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Recenzija nije pronađena");
            return "redirect:/filmovi/film-details/" + filmIdd;
        }

        Long filmId = recenzija.getFilm().getId();

        String email = authentication.getName();
        User korisnik = userRepository.findByEmail(email);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));


        boolean isAuthor = recenzija.getUser().equals(korisnik);


        if (!isAdmin && !isAuthor) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nemate pravo brisati ovu recenziju");
            return "redirect:/filmovi/film-details/" + filmId;
        }

        recenzijaRepository.delete(recenzija);
        redirectAttributes.addFlashAttribute("successMessage", "Recenzija je uspješno obrisana");
        return "redirect:/filmovi/film-details/" + filmId;
    }



    @PostMapping("/{recenzijaId}/like")
    public String addLikeToRecenzija(@PathVariable Long recenzijaId, RedirectAttributes redirectAttributes) {
        Recenzija recenzija = recenzijaRepository.findById(recenzijaId).orElse(null);
        if (recenzija != null) {
            recenzija.dodajLike();
            recenzijaRepository.save(recenzija);
            redirectAttributes.addFlashAttribute("successMessage", "Like dodan uspješno!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Recenzija nije pronađena.");
        }
        return "redirect:/filmovi/film-details/" + (recenzija != null ? recenzija.getFilm().getId() : "");
    }

    @PostMapping("/{recenzijaId}/dislike")
    public String addDislikeToRecenzija(@PathVariable Long recenzijaId, RedirectAttributes redirectAttributes) {
        Recenzija recenzija = recenzijaRepository.findById(recenzijaId).orElse(null);
        if (recenzija != null) {
            recenzija.dodajDislike();
            recenzijaRepository.save(recenzija);
            redirectAttributes.addFlashAttribute("successMessage", "Dislike dodan uspješno!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Recenzija nije pronađena.");
        }
        return "redirect:/filmovi/film-details/" + (recenzija != null ? recenzija.getFilm().getId() : "");
    }
}
