package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
    // Osnovne CRUD operacije su već dostupne
}
