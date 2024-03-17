package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.service.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/all")
    public List<Language> getAllCountries() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable Long id) {
        return languageService.getLanguageById(id);
    }

    @PostMapping("/create/")
    public String createLanguage(@RequestParam String name, @RequestParam(required = false) List<Long> countryIds) {
        return languageService.createLanguage(name, countryIds);
    }

    @PatchMapping("/changeName/{id}")
    public String updateLanguageName(@PathVariable Long id, @RequestParam String name) {
        return languageService.updateLanguageName(id, name);
    }

    @PatchMapping("/addCountry/{id}")
    public String addCountryToLanguage(@PathVariable Long id, @RequestParam Long countryId) {
        return languageService.addCountryToLanguage(id, countryId);
    }

    @PatchMapping("deleteCountry/{id}")
    public String deleteCountryFromLanguage(@PathVariable Long id, @RequestParam Long countryId) {
        return languageService.deleteCountryFromLanguage(id, countryId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLanguageById(@PathVariable Long id) {
        return languageService.deleteLanguageById(id);
    }
}
