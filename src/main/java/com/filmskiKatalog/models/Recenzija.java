package com.filmskiKatalog.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000) // Pretpostavka za duljinu teksta recenzije
    private String tekst;

    private int ocjena; // Pretpostavka da je ocjena numerička vrijednost

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id")
    private User korisnik; // Pretpostavka da postoji entitet User

    private LocalDate datumObjave;

    private int brojLikeova;
    private int brojDislikeova;

    public Recenzija() {
    }

    public Recenzija(String tekst, int ocjena, Film film, User korisnik, LocalDate datumObjave) {
        this.tekst = tekst;
        this.ocjena = ocjena;
        this.film = film;
        this.korisnik = korisnik;
        this.datumObjave = datumObjave;
        this.brojLikeova = 0; // Inicijalno postavljamo na 0
        this.brojDislikeova = 0; // Inicijalno postavljamo na 0
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

    public LocalDate getDatumObjave() {
        return datumObjave;
    }

    public void setDatumObjave(LocalDate datumObjave) {
        this.datumObjave = datumObjave;
    }

    public int getBrojLikeova() {
        return brojLikeova;
    }

    public void setBrojLikeova(int brojLikeova) {
        this.brojLikeova = brojLikeova;
    }

    public int getBrojDislikeova() {
        return brojDislikeova;
    }

    public void setBrojDislikeova(int brojDislikeova) {
        this.brojDislikeova = brojDislikeova;
    }

    // Metode za upravljanje likeovima i dislikeovima
    public void dodajLike() {
        this.brojLikeova++;
    }

    public void dodajDislike() {
        this.brojDislikeova++;
    }
}
