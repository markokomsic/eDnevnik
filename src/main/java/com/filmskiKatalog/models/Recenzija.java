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
    @JoinColumn(name = "user_id")
    private User user; // Pretpostavka da postoji entitet User

    private LocalDate datumObjave;

    private int brojLikeova;
    private int brojDislikeova;

    public Recenzija() {
        this.datumObjave = LocalDate.now();
        this.brojLikeova = 0;
        this.brojDislikeova = 0;
    }


    public Recenzija(String tekst, int ocjena, Film film, User user, LocalDate datumObjave) {
        this.tekst = tekst;
        this.ocjena = ocjena;
        this.film = film;
        this.user = user;
        this.datumObjave = datumObjave != null ? datumObjave : LocalDate.now(); // Postavlja se na proslijeđeni datum ili na trenutni datum ako je proslijeđeni datum null
        this.brojLikeova = 0;
        this.brojDislikeova = 0;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
