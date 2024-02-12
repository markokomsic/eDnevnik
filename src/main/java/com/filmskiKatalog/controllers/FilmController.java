package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Film;
import com.filmskiKatalog.models.Zanr;
import com.filmskiKatalog.services.FilmService;
import com.filmskiKatalog.services.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/filmovi")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private ZanrService zanrService;

    // Prikazuje listu svih filmova
    @GetMapping("/film-list")
    public String listAllFilms(Model model) {
        List<Film> filmovi = filmService.findAllFilms();
        model.addAttribute("filmovi", filmovi);
        return "filmovi/film-list";
    }

    // Prikazuje formu za dodavanje novog filma
    @GetMapping("/add-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddFilmForm(Model model) {
        List<Zanr> zanrovi = zanrService.findAllZanrovi();
        model.addAttribute("zanrovi",zanrovi);
        model.addAttribute("film", new Film());
        return "filmovi/add-film";
    }

    // Obrada forme za dodavanje novog filma
    @PostMapping("/add-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addFilm(@ModelAttribute("film") Film film, RedirectAttributes redirectAttributes) {
        filmService.saveFilm(film);
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno dodan!");
        return "redirect:/filmovi/film-list";
    }

    // Prikazuje formu za uređivanje postojećeg filma
    @GetMapping("/edit-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditFilmForm(@PathVariable Long id, Model model) {
        List<Zanr> zanrovi = zanrService.findAllZanrovi(); // Ponovno učitavanje žanrova za uređivanje
        Optional<Film> filmOptional = filmService.findFilmById(id);
        filmOptional.ifPresent(film -> model.addAttribute("film", film));
        model.addAttribute("zanrovi", zanrovi); // Dodavanje žanrova u model za formu uređivanja
        return filmOptional.isPresent() ? "filmovi/edit-film" : "redirect:/filmovi/film-list";
    }

    // Obrada forme za uređivanje postojećeg filma
    @PostMapping("/edit-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateFilm(@PathVariable Long id, @ModelAttribute("film") Film film, RedirectAttributes redirectAttributes) {
        filmService.saveFilm(film); // Pretpostavljamo da save metoda ažurira film ako postoji ID
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno ažuriran!");
        return "redirect:/filmovi/film-list";
    }

    // Brisanje filma
    @GetMapping("/delete-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteFilm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        filmService.deleteFilm(id);
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno obrisan!");
        return "redirect:/filmovi/film-list";
    }
    @GetMapping("/film-details/{id}")
    public String showFilmDetails(@PathVariable Long id, Model model) {
        Optional<Film> film = filmService.findFilmById(id);
        if (film.isPresent()) {
            model.addAttribute("film", film.get());
            return "filmovi/film-details"; // Pretpostavlja se da imate template pod ovim imenom
        } else {
            return "redirect:/filmovi/film-list"; // Ili neki drugi odgovarajući handling za "film not found"
        }
    }
}
