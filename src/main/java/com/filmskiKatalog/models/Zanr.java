package com.filmskiKatalog.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Zanr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @ManyToMany(mappedBy = "zanrovi", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Film> filmovi = new HashSet<>();

    // Defaultni konstruktor
    public Zanr() {
    }

    // Parametrizirani konstruktor
    public Zanr(String naziv) {
        this.naziv = naziv;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<Film> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(Set<Film> filmovi) {
        this.filmovi = filmovi;
    }

    // Metode za upravljanje vezama
    public void addFilm(Film film) {
        this.filmovi.add(film);
        film.getZanrovi().add(this);
    }

    public void removeFilm(Film film) {
        this.filmovi.remove(film);
        film.getZanrovi().remove(this);
    }
}