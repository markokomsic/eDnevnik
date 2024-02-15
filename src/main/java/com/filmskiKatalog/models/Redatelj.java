package com.filmskiKatalog.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Redatelj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    @Column(length = 10000)
    private String biografija;
    private String slikaUrl;
    private LocalDate datumRodjenja;
    private String mjestoRodjenja;

    @ManyToMany(mappedBy = "redatelji", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Film> filmovi = new HashSet<>();

    public Redatelj() {
    }

    public Redatelj(String ime, String biografija, String slikaUrl, LocalDate datumRodjenja, String mjestoRodjenja) {
        this.ime = ime;
        this.biografija = biografija;
        this.slikaUrl = slikaUrl;
        this.datumRodjenja = datumRodjenja;
        this.mjestoRodjenja = mjestoRodjenja;
    }

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

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    public String getSlikaUrl() {
        return slikaUrl;
    }

    public void setSlikaUrl(String slikaUrl) {
        this.slikaUrl = slikaUrl;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getMjestoRodjenja() {
        return mjestoRodjenja;
    }

    public void setMjestoRodjenja(String mjestoRodjenja) {
        this.mjestoRodjenja = mjestoRodjenja;
    }

    public Set<Film> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(Set<Film> filmovi) {
        this.filmovi = filmovi;
    }

    public void addFilm(Film film) {
        this.filmovi.add(film);
        film.getRedatelji().add(this);
    }

    public void removeFilm(Film film) {
        this.filmovi.remove(film);
        film.getRedatelji().remove(this);
    }
}
