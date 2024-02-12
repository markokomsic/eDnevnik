package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
    // Ovdje možete dodati metode za dohvat recenzija po filmu, korisniku, itd.
}
