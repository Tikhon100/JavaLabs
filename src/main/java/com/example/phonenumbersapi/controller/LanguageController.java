package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.service.CountryService;
import com.example.phonenumbersapi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/all")
    public ResponseEntity<List<Language>> getAllCountries() {
        List<Language> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable("id") Long id) {
        Language language = languageService.getLanguageById(id);
        return ResponseEntity.ok(language);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createLanguage(@RequestBody Language language) {
        languageService.saveLanguage(language);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLanguage(@PathVariable("id") Long id,
                                               @RequestBody Language language) {
        languageService.updateLanguage(id, language);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable("id") Long id) {
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
