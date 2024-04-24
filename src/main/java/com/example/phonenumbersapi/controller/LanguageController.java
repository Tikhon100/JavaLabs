package com.example.phonenumbersapi.controller;


import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@MyExceptionHandler
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/language")
@CrossOrigin(origins = "*")
public class LanguageController {

    private final LanguageService languageService;

    @Operation(summary = "Получить список всех языков")
    @GetMapping("/all")
    public List<Language> getAllCountries() {
        return languageService.getAllLanguages();
    }

    @Operation(summary = "Получить язык по id")
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(final @PathVariable Long id) {
        return languageService.getLanguageById(id);
    }

    @Operation(summary = "Создать язык",
            description = "Запрос добавляет язык в базу данных приложения")
    @PostMapping("/create/")
    public ResponseEntity<String> createLanguage(final @RequestParam String name, final @RequestParam(required = false) List<Long> countryIds) {
        return languageService.createLanguage(name, countryIds);
    }

    @Operation(summary = "Изменить имя языка")
    @PatchMapping("/changeName/{id}")
    public ResponseEntity<String> updateLanguageName(final @PathVariable Long id, final @RequestParam String name) {
        return languageService.updateLanguageName(id, name);
    }

    @Operation(summary = "Добавить страну к языку")
    @PatchMapping("/addCountry/{id}")
    public String addCountryToLanguage(final @PathVariable Long id, final @RequestParam Long countryId) {
        return languageService.addCountryToLanguage(id, countryId);
    }

    @Operation(summary = "Удалить страну из языка")
    @PatchMapping("deleteCountry/{id}")
    public String deleteCountryFromLanguage(final @PathVariable Long id, final @RequestParam Long countryId) {
        return languageService.deleteCountryFromLanguage(id, countryId);
    }

    @Operation(summary = "Удалить язык из базы данных")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLanguageById(final @PathVariable Long id) {
        return languageService.deleteLanguageById(id);
    }
}
