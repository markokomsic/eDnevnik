package com.filmskiKatalog.services;

import com.filmskiKatalog.models.Film;
import com.filmskiKatalog.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;

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

    public List<Film> searchByQuery(String query) {

        return filmRepository.findByNazivContainingIgnoreCase(query);
    }
    public List<Film> findAllSortedByOcjena() {
        return filmRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Film::getProsjecnaOcjena).reversed()) // Sortiranje od najveće prema najmanjoj ocjeni
                .collect(Collectors.toList());
    }
}
