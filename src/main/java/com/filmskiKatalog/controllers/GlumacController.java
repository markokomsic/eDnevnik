package com.filmskiKatalog.controllers;

import com.filmskiKatalog.models.Glumac;
import com.filmskiKatalog.services.GlumacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/glumci")
public class GlumacController {

    @Autowired
    private GlumacService glumacService;

    @GetMapping("/glumac-list")
    public String listAllGlumci(Model model) {
        List<Glumac> glumci = glumacService.findAllGlumci();
        model.addAttribute("glumci", glumci);
        return "glumci/glumac-list";
    }

    @GetMapping("/add-glumac")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddGlumacForm(Model model) {
        model.addAttribute("glumac", new Glumac());
        return "glumci/add-glumac";
    }

    @PostMapping("/add-glumac")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addGlumac(@ModelAttribute("glumac") Glumac glumac, RedirectAttributes redirectAttributes) {
        glumacService.saveGlumac(glumac);
        redirectAttributes.addFlashAttribute("successMessage", "Glumac je uspješno dodan!");
        return "redirect:/glumci/glumac-list";
    }

    @GetMapping("/edit-glumac/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditGlumacForm(@PathVariable Long id, Model model) {
        Optional<Glumac> glumacOptional = glumacService.findGlumacById(id);
        glumacOptional.ifPresent(glumac -> model.addAttribute("glumac", glumac));
        return glumacOptional.isPresent() ? "glumci/edit-glumac" : "redirect:/glumci/glumac-list";
    }

    @PostMapping("/edit-glumac/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateGlumac(@PathVariable Long id, @ModelAttribute("glumac") Glumac glumac, RedirectAttributes redirectAttributes) {
        glumacService.saveGlumac(glumac); // Pretpostavljamo da save metoda ažurira glumca ako postoji ID
        redirectAttributes.addFlashAttribute("successMessage", "Glumac je uspješno ažuriran!");
        return "redirect:/glumci/glumac-list";
    }

    @GetMapping("/delete-glumac/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteGlumac(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        glumacService.deleteGlumac(id);
        redirectAttributes.addFlashAttribute("successMessage", "Glumac je uspješno obrisan!");
        return "redirect:/glumci/glumac-list";
    }
    @GetMapping("/glumac-details/{id}")
    public String showGlumacDetails(@PathVariable Long id, Model model) {
        Optional<Glumac> glumacOptional = glumacService.findGlumacById(id);
        if (glumacOptional.isPresent()) {
            model.addAttribute("glumac", glumacOptional.get());
            return "glumci/glumac-details";
        } else {
            return "redirect:/glumci/glumac-list";
        }
    }

}
