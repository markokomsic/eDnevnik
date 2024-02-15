package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    // Metoda za pretragu filmova po nazivu (sadrži neovisno o veličini slova)
    List<Film> findByNazivContainingIgnoreCase(String naziv);
}
