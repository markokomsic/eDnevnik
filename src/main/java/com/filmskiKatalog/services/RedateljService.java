package com.filmskiKatalog.services;

import com.filmskiKatalog.models.Redatelj;
import com.filmskiKatalog.repositories.RedateljRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedateljService {
    @Autowired
    private RedateljRepository redateljRepository;

    public List<Redatelj> findAllRedatelji() {
        return redateljRepository.findAll();
    }

    public Optional<Redatelj> findRedateljById(Long id) {
        return redateljRepository.findById(id);
    }

    public Redatelj saveRedatelj(Redatelj redatelj) {
        return redateljRepository.save(redatelj);
    }

    public void deleteRedatelj(Long id) {
        redateljRepository.deleteById(id);
    }

    // Dodajte dodatne metode prema potrebi
}
