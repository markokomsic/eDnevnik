package com.filmskiKatalog.services;

import com.filmskiKatalog.models.Film;
import com.filmskiKatalog.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> findFilmById(Long id) {
        return filmRepository.findById(id);
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    // Dodajte dodatne metode prema potrebi
}
