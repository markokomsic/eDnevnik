package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Glumac;
import com.filmskiKatalog.services.GlumacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/glumci")
public class GlumacController {

    @Autowired
    private GlumacService glumacService;

    @GetMapping
    public List<Glumac> getAllGlumci() {
        return glumacService.findAllGlumci();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Glumac addGlumac(@RequestBody Glumac glumac) {
        return glumacService.saveGlumac(glumac);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGlumac(@PathVariable Long id) {
        glumacService.deleteGlumac(id);
    }
}
