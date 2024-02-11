package com.filmskiKatalog.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Redatelj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    @ManyToMany(mappedBy = "redatelji", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Film> filmovi = new HashSet<>();

    // Defaultni konstruktor
    public Redatelj() {
    }

    // Parametrizirani konstruktor
    public Redatelj(String ime) {
        this.ime = ime;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
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
        film.getRedatelji().add(this);
    }

    public void removeFilm(Film film) {
        this.filmovi.remove(film);
        film.getRedatelji().remove(this);
    }
}
