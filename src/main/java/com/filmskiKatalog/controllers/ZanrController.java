package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Zanr;
import com.filmskiKatalog.services.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zanr")
public class ZanrController {

    @Autowired
    private ZanrService zanrService;

    @GetMapping
    public List<Zanr> getAllZanrovi() {
        return zanrService.findAllZanrovi();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Zanr addZanr(@RequestBody Zanr zanr) {
        return zanrService.saveZanr(zanr);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteZanr(@PathVariable Long id) {
        zanrService.deleteZanr(id);
    }
}
