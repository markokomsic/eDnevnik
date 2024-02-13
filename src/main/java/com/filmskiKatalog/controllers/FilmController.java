package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Film;
import com.filmskiKatalog.models.Glumac;
import com.filmskiKatalog.models.Redatelj;
import com.filmskiKatalog.models.Zanr;
import com.filmskiKatalog.services.FilmService;
import com.filmskiKatalog.services.GlumacService;
import com.filmskiKatalog.services.RedateljService;
import com.filmskiKatalog.services.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/filmovi")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private ZanrService zanrService;
    @Autowired
    private GlumacService glumacService;
    @Autowired
    private RedateljService redateljService;

    @GetMapping("/film-list")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String listAllFilms(Model model) {
        List<Film> filmovi = filmService.findAllFilms();
        model.addAttribute("filmovi", filmovi);
        return "filmovi/film-list";
    }

    @GetMapping("/add-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddFilmForm(Model model) {
        List<Zanr> zanrovi = zanrService.findAllZanrovi();
        List<Glumac> glumci = glumacService.findAllGlumci();
        List<Redatelj> redatelji = redateljService.findAllRedatelji();
        model.addAttribute("zanrovi", zanrovi);
        model.addAttribute("glumci", glumci);
        model.addAttribute("redatelji", redatelji);
        model.addAttribute("film", new Film());
        return "filmovi/add-film";
    }

    @PostMapping("/add-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addFilm(@ModelAttribute("film") Film film, RedirectAttributes redirectAttributes,
                          @RequestParam("selectedZanrovi") Set<Long> selectedZanrovi,
                          @RequestParam("selectedGlumci") Set<Long> selectedGlumci,
                          @RequestParam("selectedRedatelji") Set<Long> selectedRedatelji) {
        Set<Zanr> zanrovi = new HashSet<>(zanrService.findAllById(selectedZanrovi));
        Set<Glumac> glumci = new HashSet<>(glumacService.findAllById(selectedGlumci));
        Set<Redatelj> redatelji = new HashSet<>(redateljService.findAllById(selectedRedatelji));

        film.setZanrovi(zanrovi);
        film.setGlumci(glumci);
        film.setRedatelji(redatelji);

        filmService.saveFilm(film);
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno dodan!");
        return "redirect:/filmovi/film-list";
    }

    @GetMapping("/edit-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditFilmForm(@PathVariable Long id, Model model) {
        Optional<Film> filmOptional = filmService.findFilmById(id);
        if (filmOptional.isPresent()) {
            List<Zanr> zanrovi = zanrService.findAllZanrovi();
            List<Glumac> glumci = glumacService.findAllGlumci();
            List<Redatelj> redatelji = redateljService.findAllRedatelji();
            model.addAttribute("zanrovi", zanrovi);
            model.addAttribute("glumci", glumci);
            model.addAttribute("redatelji", redatelji);
            model.addAttribute("film", filmOptional.get());
            return "filmovi/edit-film";
        } else {
            model.addAttribute("errorMessage", "Film nije pronađen!");
            return "redirect:/filmovi/film-list";
        }
    }


    @PostMapping("/edit-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateFilm(@PathVariable Long id, @ModelAttribute("film") Film film, RedirectAttributes redirectAttributes,
                             @RequestParam("selectedZanrovi") Set<Long> selectedZanrovi,
                             @RequestParam("selectedGlumci") Set<Long> selectedGlumci,
                             @RequestParam("selectedRedatelji") Set<Long> selectedRedatelji) {
        Set<Zanr> zanrovi = new HashSet<>(zanrService.findAllById(selectedZanrovi));
        Set<Glumac> glumci = new HashSet<>(glumacService.findAllById(selectedGlumci));
        Set<Redatelj> redatelji = new HashSet<>(redateljService.findAllById(selectedRedatelji));

        film.setZanrovi(zanrovi);
        film.setGlumci(glumci);
        film.setRedatelji(redatelji);

        filmService.saveFilm(film);
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno ažuriran!");
        return "redirect:/filmovi/film-list";
    }

    @GetMapping("/delete-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteFilm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        filmService.deleteFilm(id);
        redirectAttributes.addFlashAttribute("successMessage", "Film je uspješno obrisan!");
        return "redirect:/filmovi/film-list";
    }

    @GetMapping("/film-details/{id}")
    public String showFilmDetails(@PathVariable Long id, Model model) {
        Optional<Film> filmOptional = filmService.findFilmById(id);
        filmOptional.ifPresent(film -> model.addAttribute("film", film));
        return filmOptional.isPresent() ? "filmovi/film-details" : "redirect:/filmovi/film-list";
    }
}
