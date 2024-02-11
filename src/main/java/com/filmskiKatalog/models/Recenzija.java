package com.filmskiKatalog.models;

import jakarta.persistence.*;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000) // Pretpostavka za duljinu teksta recenzije
    private String tekst;

    private int ocjena; // Pretpostavka da je ocjena numerička vrijednost

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id")
    private User korisnik; // Pretpostavka da postoji entitet User

    // Defaultni konstruktor
    public Recenzija() {
    }

    // Parametrizirani konstruktor
    public Recenzija(String tekst, int ocjena, Film film, User korisnik) {
        this.tekst = tekst;
        this.ocjena = ocjena;
        this.film = film;
        this.korisnik = korisnik;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(User korisnik) {
        this.korisnik = korisnik;
    }
}