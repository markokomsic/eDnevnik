package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Film;
import com.filmskiKatalog.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmovi")
public class FilmController {

    @Autowired
    private FilmService filmService;

    // Svi mogu pregledavati filmove
    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.findAllFilms();
    }

    // Samo admin može dodavati filmove
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Film addFilm(@RequestBody Film film) {
        return filmService.saveFilm(film);
    }

    // Samo admin može brisati filmove
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
}