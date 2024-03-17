package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/all")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/getCountiesByLanguages")
    public List<Country> getCountiesByLanguages(@RequestParam List<String> languages) {
        return countryService.getCountriesByLanguages(languages);
    }


    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @PostMapping("/create")
    public String createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PatchMapping("/updateName/{id}")
    public String updateNameCountry(@PathVariable Long id, @RequestParam String name) {
        return countryService.updateNameCountry(id, name);
    }

    @PatchMapping("addPhoneNumberCode/{id}")
    public String addPhoneNumberCode(@PathVariable Long id, @RequestParam String code) {
        return countryService.addPhoneNumberCode(id, code);
    }

    @PatchMapping("/addLanguageToCountry/{id}")
    public String addLanguageToCountry(@PathVariable Long id, @RequestParam Long languageId) {
        return countryService.addLanguageToCountry(id, languageId);
    }

    @PatchMapping("/deleteLanguageFromCountry/{id}")
    public String deleteLanguageFromCountry(@PathVariable Long id, @RequestParam Long languageId) {
        return countryService.deleteLanguageFromCountry(id, languageId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return "Successfully deleted";
    }
}
