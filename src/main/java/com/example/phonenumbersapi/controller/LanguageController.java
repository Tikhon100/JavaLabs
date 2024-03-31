package com.example.phonenumbersapi.controller;


import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

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
    public Language getLanguageById(@PathVariable Long id) {
        return languageService.getLanguageById(id);
    }

    @Operation(summary = "Создать язык",
            description = "Запрос добавляет язык в базу данных приложения, к нему можно сразу привязать страны или сделать это позже")
    @PostMapping("/create/")
    public String createLanguage(@RequestParam String name, @RequestParam(required = false) List<Long> countryIds) {
        return languageService.createLanguage(name, countryIds);
    }

    @Operation(summary = "Изменить имя языка")
    @PatchMapping("/changeName/{id}")
    public String updateLanguageName(@PathVariable Long id, @RequestParam String name) {
        return languageService.updateLanguageName(id, name);
    }

    @Operation(summary = "Добавить страну к языку")
    @PatchMapping("/addCountry/{id}")
    public String addCountryToLanguage(@PathVariable Long id, @RequestParam Long countryId) {
        return languageService.addCountryToLanguage(id, countryId);
    }

    @Operation(summary = "Удалить страну из языка")
    @PatchMapping("deleteCountry/{id}")
    public String deleteCountryFromLanguage(@PathVariable Long id, @RequestParam Long countryId) {
        return languageService.deleteCountryFromLanguage(id, countryId);
    }

    @Operation(summary = "Удалить язык из базы данных")
    @DeleteMapping("/delete/{id}")
    public String deleteLanguageById(@PathVariable Long id) {
        return languageService.deleteLanguageById(id);
    }
}
