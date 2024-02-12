package com.filmskiKatalog.services;

import com.filmskiKatalog.models.Zanr;
import com.filmskiKatalog.repositories.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZanrService {
    @Autowired
    private ZanrRepository zanrRepository;

    public List<Zanr> findAllZanrovi() {
        return zanrRepository.findAll();
    }

    public Optional<Zanr> findZanrById(Long id) {
        return zanrRepository.findById(id);
    }

    public Zanr saveZanr(Zanr zanr) {
        return zanrRepository.save(zanr);
    }

    public void deleteZanr(Long id) {
        zanrRepository.deleteById(id);
    }

    // Dodajte dodatne metode prema potrebi, npr. pretraga filmova po zanru
}
