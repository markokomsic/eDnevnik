package com.filmskiKatalog.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private String opis;
    private int godinaIzdavanja;
    private String slikaUrl; // URL slike filma
    private int trajanje; // Trajanje filma u minutama
    private String trailerUrl; // URL trailera filma

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "film_zanr",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "zanr_id")
    )
    private Set < Zanr > zanrovi = new HashSet < > ();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set < Recenzija > recenzije = new HashSet < > ();

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "film_glumci",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "glumac_id")
    )
    private Set < Glumac > glumci = new HashSet < > ();

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "film_redatelji",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "redatelj_id")
    )
    private Set < Redatelj > redatelji = new HashSet < > ();

    // Defaultni konstruktor
    public Film() {}

    // Parametrizirani konstruktor
    public Film(String naziv, String opis, int godinaIzdavanja, String slikaUrl, int trajanje, String trailerUrl) {
        this.naziv = naziv;
        this.opis = opis;
        this.godinaIzdavanja = godinaIzdavanja;
        this.slikaUrl = slikaUrl;
        this.trajanje = trajanje;
        this.trailerUrl = trailerUrl;
    }

    // Getteri i setteri za sve atribute, uključujući nove
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
    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }
    public int getGodinaIzdavanja() {
        return godinaIzdavanja;
    }
    public void setGodinaIzdavanja(int godinaIzdavanja) {
        this.godinaIzdavanja = godinaIzdavanja;
    }
    public String getSlikaUrl() {
        return slikaUrl;
    }
    public void setSlikaUrl(String slikaUrl) {
        this.slikaUrl = slikaUrl;
    }
    public int getTrajanje() {
        return trajanje;
    }
    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }
    public String getTrailerUrl() {
        return trailerUrl;
    }
    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }
    public Set < Zanr > getZanrovi() {
        return zanrovi;
    }
    public void setZanrovi(Set < Zanr > zanrovi) {
        this.zanrovi = zanrovi;
    }
    public Set < Recenzija > getRecenzije() {
        return recenzije;
    }
    public void setRecenzije(Set < Recenzija > recenzije) {
        this.recenzije = recenzije;
    }
    public Set < Glumac > getGlumci() {
        return glumci;
    }
    public void setGlumci(Set < Glumac > glumci) {
        this.glumci = glumci;
    }
    public Set < Redatelj > getRedatelji() {
        return redatelji;
    }
    public void setRedatelji(Set < Redatelj > redatelji) {
        this.redatelji = redatelji;
    }

    // Metode za upravljanje vezama
    public void addZanr(Zanr zanr) {
        zanrovi.add(zanr);
        zanr.getFilmovi().add(this);
    }
    public void removeZanr(Zanr zanr) {
        zanrovi.remove(zanr);
        zanr.getFilmovi().remove(this);
    }
    public void addRecenzija(Recenzija recenzija) {
        recenzije.add(recenzija);
        recenzija.setFilm(this);
    }
    public void removeRecenzija(Recenzija recenzija) {
        recenzije.remove(recenzija);
        recenzija.setFilm(null);
    }
    public void addGlumac(Glumac glumac) {
        glumci.add(glumac);
        glumac.getFilmovi().add(this);
    }
    public void removeGlumac(Glumac glumac) {
        glumci.remove(glumac);
        glumac.getFilmovi().remove(this);
    }
    public void addRedatelj(Redatelj redatelj) {
        redatelji.add(redatelj);
        redatelj.getFilmovi().add(this);
    }
    public void removeRedatelj(Redatelj redatelj) {
        redatelji.remove(redatelj);
        redatelj.getFilmovi().remove(this);
    }
}