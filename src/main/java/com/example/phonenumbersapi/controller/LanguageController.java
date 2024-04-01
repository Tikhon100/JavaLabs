package com.example.phonenumbersapi.controller;


import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@MyExceptionHandler
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    @Operation(summary = "Получить список всех языков")
    @GetMapping("/all")
    public List<Language> getAllCountries() {
        return languageService.getAllLanguages();
    }

    @Operation(summary = "Получить язык по id")
    @GetMapping("/{id}")
    public Language getLanguageById(final @PathVariable Long id) {
        return languageService.getLanguageById(id);
    }

    @Operation(summary = "Создать язык",
            description = "Запрос добавляет язык в базу данных приложения")
    @PostMapping("/create/")
    public String createLanguage(final @RequestParam String name, final @RequestParam(required = false) List<Long> countryIds) {
        return languageService.createLanguage(name, countryIds);
    }

    @Operation(summary = "Изменить имя языка")
    @PatchMapping("/changeName/{id}")
    public String updateLanguageName(final @PathVariable Long id, final @RequestParam String name) {
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
    public String deleteLanguageById(final @PathVariable Long id) {
        return languageService.deleteLanguageById(id);
    }
}
