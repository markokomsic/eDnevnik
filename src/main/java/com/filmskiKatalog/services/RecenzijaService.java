package com.filmskiKatalog.services;

import com.filmskiKatalog.models.Recenzija;
import com.filmskiKatalog.repositories.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecenzijaService {
    @Autowired
    private RecenzijaRepository recenzijaRepository;

    public List<Recenzija> findAllRecenzije() {
        return recenzijaRepository.findAll();
    }

    public Optional<Recenzija> findRecenzijaById(Long id) {
        return recenzijaRepository.findById(id);
    }

    public Recenzija saveRecenzija(Recenzija recenzija) {
        return recenzijaRepository.save(recenzija);
    }

    public List<Recenzija> findAllByFilmId(Long filmId) {
        return recenzijaRepository.findAllByFilmId(filmId);
    }


    public void deleteRecenzija(Long id) {
        recenzijaRepository.deleteById(id);
    }


}
