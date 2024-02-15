package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.*;
import com.filmskiKatalog.repositories.UserRepository;
import com.filmskiKatalog.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
    @Autowired
    private RecenzijaService recenzijaService;
    @Autowired
    private UserRepository userRepository;



    @GetMapping("/film-list")
    @PreAuthorize("hasAuthority('ADMIN')")
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
            Film film = filmOptional.get();
            List<Zanr> zanrovi = zanrService.findAllZanrovi();
            List<Glumac> glumci = glumacService.findAllGlumci();
            List<Redatelj> redatelji = redateljService.findAllRedatelji();
            model.addAttribute("zanrovi", zanrovi);
            model.addAttribute("glumci", glumci);
            model.addAttribute("redatelji", redatelji);
            model.addAttribute("film", film); // Ovdje se šalje film sa njegovim trenutnim žanrovima
            return "filmovi/edit-film";
        } else {
            model.addAttribute("errorMessage", "Film nije pronađen!");
            return "redirect:/filmovi/film-list";
        }
    }


    @PostMapping("/edit-film/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateFilm(@PathVariable Long id, @ModelAttribute("film") Film updatedFilm, RedirectAttributes redirectAttributes,
                             @RequestParam(value = "selectedZanrovi", required = false) Optional<Set<Long>> selectedZanrovi,
                             @RequestParam(value = "selectedGlumci", required = false) Optional<Set<Long>> selectedGlumci,
                             @RequestParam(value = "selectedRedatelji", required = false) Optional<Set<Long>> selectedRedatelji) {
        Film existingFilm = filmService.findFilmById(id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID filma: " + id));


        if (selectedZanrovi.isPresent() && !selectedZanrovi.get().isEmpty()) {
            Set<Zanr> zanrovi = new HashSet<>(zanrService.findAllById(selectedZanrovi.get()));
            updatedFilm.setZanrovi(zanrovi);
        } else {
            updatedFilm.setZanrovi(existingFilm.getZanrovi());
        }


        if (selectedGlumci.isPresent() && !selectedGlumci.get().isEmpty()) {
            Set<Glumac> glumci = new HashSet<>(glumacService.findAllById(selectedGlumci.get()));
            updatedFilm.setGlumci(glumci);
        } else {
            updatedFilm.setGlumci(existingFilm.getGlumci());
        }


        if (selectedRedatelji.isPresent() && !selectedRedatelji.get().isEmpty()) {
            Set<Redatelj> redatelji = new HashSet<>(redateljService.findAllById(selectedRedatelji.get()));
            updatedFilm.setRedatelji(redatelji);
        } else {
            updatedFilm.setRedatelji(existingFilm.getRedatelji());
        }

        updatedFilm.setId(id);

        filmService.saveFilm(updatedFilm);
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
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            model.addAttribute("film", film);
            List<Recenzija> recenzije = recenzijaService.findAllByFilmId(film.getId()); // Koristite novu metodu
            model.addAttribute("recenzije", recenzije);
            model.addAttribute("novaRecenzija", new Recenzija()); // Za formu dodavanja nove recenzije
            return "filmovi/film-details";
        } else {
            return "redirect:/filmovi/home";
        }
    }

    @PostMapping("/addRecenzija")
    public String addRecenzija(@ModelAttribute("novaRecenzija") Recenzija novaRecenzija,
                               @RequestParam("filmId") Long filmId, Principal principal) {
        String email = principal.getName(); // Dohvaća e-mail trenutno prijavljenog korisnika
        User user = userRepository.findByEmail(email); // Pretpostavljamo da metoda findByEmail postoji

        if (user == null) {
            throw new UsernameNotFoundException("Korisnik nije pronađen s e-mailom: " + email);
        }

        Film film = filmService.findFilmById(filmId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID filma: " + filmId));
        novaRecenzija.setUser(user); // Postavljanje korisnika za recenziju
        novaRecenzija.setFilm(film); // Postavljanje filma za recenziju
        recenzijaService.saveRecenzija(novaRecenzija); // Spremanje recenzije
        return "redirect:/filmovi/film-details/" + filmId; // Preusmjeravanje na stranicu s detaljima filma
    }





    @GetMapping("/home")
    public String home(Model model) {
        List<Film> filmovi = filmService.findAllFilms();
        model.addAttribute("filmovi", filmovi);
        return "filmovi/home";
    }

    @PostMapping("/recenzija/{recenzijaId}/like")
    public String addLikeToRecenzija(@PathVariable Long recenzijaId) {
        Recenzija recenzija = recenzijaService.findRecenzijaById(recenzijaId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID recenzije: " + recenzijaId));
        recenzija.dodajLike();
        recenzijaService.saveRecenzija(recenzija);
        return "redirect:/film-details/" + recenzija.getFilm().getId();
    }

    @PostMapping("/recenzija/{recenzijaId}/dislike")
    public String addDislikeToRecenzija(@PathVariable Long recenzijaId) {
        Recenzija recenzija = recenzijaService.findRecenzijaById(recenzijaId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID recenzije: " + recenzijaId));
        recenzija.dodajDislike();
        recenzijaService.saveRecenzija(recenzija);
        return "redirect:/film-details/" + recenzija.getFilm().getId();
    }
    @GetMapping("/search")
    public String searchFilms(@RequestParam("query") String query, Model model) {
        List<Film> searchResults = filmService.searchByQuery(query);
        model.addAttribute("filmovi", searchResults);
        return "filmovi/home";
    }

    @GetMapping("/sortiraniPoOcjeni")
    public String getFilmsSortedByOcjena(Model model) {
        List<Film> sortedFilms = filmService.findAllSortedByOcjena();
        model.addAttribute("filmovi", sortedFilms);
        return "filmovi/home";
    }

}
