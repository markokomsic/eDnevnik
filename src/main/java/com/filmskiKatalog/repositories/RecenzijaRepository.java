package com.filmskiKatalog.repositories;

import com.filmskiKatalog.models.Recenzija;
import com.filmskiKatalog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
    // Metoda za dohvat svih recenzija za određeni film po filmovom ID-u
    List<Recenzija> findAllByFilmId(Long filmId);

    // Dodajte metodu za dohvat svih recenzija koje je napisao određeni korisnik
    List<Recenzija> findByUser(User user);
}
