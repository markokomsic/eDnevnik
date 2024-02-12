package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Glumac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlumacRepository extends JpaRepository<Glumac, Long> {
    // Osnovne CRUD operacije su već dostupne
}
