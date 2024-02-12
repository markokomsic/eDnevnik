package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Redatelj;
import com.filmskiKatalog.services.RedateljService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/redatelji")
public class RedateljController {

    @Autowired
    private RedateljService redateljService;

    @GetMapping("/redatelj-list")
    public String listAllRedatelji(Model model) {
        model.addAttribute("redatelji", redateljService.findAllRedatelji());
        return "redatelji/redatelj-list";
    }

    @GetMapping("/add-redatelj")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddRedateljForm(Model model) {
        model.addAttribute("redatelj", new Redatelj());
        return "redatelji/add-redatelj";
    }

    @PostMapping("/add-redatelj")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRedatelj(@ModelAttribute Redatelj redatelj, RedirectAttributes redirectAttributes) {
        redateljService.saveRedatelj(redatelj);
        redirectAttributes.addFlashAttribute("successMessage", "Redatelj uspješno dodan!");
        return "redirect:/redatelji/redatelj-list";
    }

    @GetMapping("/edit-redatelj/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditRedateljForm(@PathVariable Long id, Model model) {
        Redatelj redatelj = redateljService.findRedateljById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid redatelj Id:" + id));
        model.addAttribute("redatelj", redatelj);
        return "redatelji/edit-redatelj";
    }

    @PostMapping("/edit-redatelj/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateRedatelj(@PathVariable Long id, @ModelAttribute Redatelj redatelj, RedirectAttributes redirectAttributes) {
        redateljService.saveRedatelj(redatelj);
        redirectAttributes.addFlashAttribute("successMessage", "Redatelj uspješno ažuriran!");
        return "redirect:/redatelji/redatelj-list";
    }

    @GetMapping("/delete-redatelj/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRedatelj(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redateljService.deleteRedatelj(id);
        redirectAttributes.addFlashAttribute("successMessage", "Redatelj uspješno obrisan!");
        return "redirect:/redatelji/redatelj-list";
    }

    @GetMapping("/redatelj-details/{id}")
    public String showRedateljDetails(@PathVariable Long id, Model model) {
        Optional<Redatelj> redateljOptional = redateljService.findRedateljById(id);
        if (redateljOptional.isPresent()) {
            model.addAttribute("redatelj", redateljOptional.get());
            return "redatelji/redatelj-details";
        } else {
            return "redirect:/redatelji/redatelj-list";
        }
    }

}
