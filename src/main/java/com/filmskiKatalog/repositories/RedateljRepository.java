package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Redatelj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedateljRepository extends JpaRepository<Redatelj, Long> {
    // Osnovne CRUD operacije su već dostupne
}
