package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Redatelj;
import com.filmskiKatalog.services.RedateljService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redatelji")
public class RedateljController {

    @Autowired
    private RedateljService redateljService;

    @GetMapping
    public List<Redatelj> getAllRedatelji() {
        return redateljService.findAllRedatelji();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Redatelj addRedatelj(@RequestBody Redatelj redatelj) {
        return redateljService.saveRedatelj(redatelj);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRedatelj(@PathVariable Long id) {
        redateljService.deleteRedatelj(id);
    }
}
