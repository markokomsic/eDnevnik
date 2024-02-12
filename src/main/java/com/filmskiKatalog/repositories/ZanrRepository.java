package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZanrRepository extends JpaRepository<Zanr, Long> {
    // Ovdje možete dodati metode za dohvat žanrova ili filmova određenog žanra
}
