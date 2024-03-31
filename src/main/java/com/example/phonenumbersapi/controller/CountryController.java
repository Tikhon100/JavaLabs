package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;


import java.util.List;

@MyExceptionHandler
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/country")
public final class CountryController {
    private final CountryService countryService;

    @Operation(summary = "Получить список всех стран",
            description = "Вам вернется список всех стран с их телефонными кодами и языками")
    @GetMapping("/all")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @Operation(summary = "Получить список стран по языкам")
    @GetMapping("/getCountiesByLanguages")
    public List<Country> getCountiesByLanguages(final @RequestParam List<String> languages) {
        return countryService.getCountriesByLanguages(languages);
    }

    @Operation(summary = "Получить страну по id",
            description = "Вам вернется страна с ее телефонными кодами и языками, соответствующая id")
    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @Operation(summary = "Создать страну",
            description = "Этот запрос создает новую страну в базе данных")
    @PostMapping("/create")
    public String createCountry(@RequestBody Country country) throws HttpClientErrorException.BadRequest {

        return countryService.createCountry(country);
    }

    @Operation(summary = "Обновить имя страны")
    @PatchMapping("/updateName/{id}")
    public String updateNameCountry(@PathVariable Long id, @RequestParam String name) {
        return countryService.updateNameCountry(id, name);
    }

    @Operation(summary = "Добавить телефонный код в страну")
    @PatchMapping("addPhoneNumberCode/{id}")
    public String addPhoneNumberCode(@PathVariable Long id, @RequestParam String code) {
        return countryService.addPhoneNumberCode(id, code);
    }

    @Operation(summary = "Добавить язык в страну")
    @PatchMapping("/addLanguageToCountry/{id}")
    public String addLanguageToCountry(@PathVariable Long id, @RequestParam Long languageId) {
        return countryService.addLanguageToCountry(id, languageId);
    }

    @Operation(summary = "Удалить язык из страны")
    @PatchMapping("/deleteLanguageFromCountry/{id}")
    public String deleteLanguageFromCountry(@PathVariable Long id, @RequestParam Long languageId) {
        return countryService.deleteLanguageFromCountry(id, languageId);
    }

    @Operation(summary = "Удалить страну",
            description = "Запрос удаляет страну вместе с ее телефоными кодами, но оставляет языки")
    @DeleteMapping("/delete/{id}")
    public String deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return "Successfully deleted";
    }
}
