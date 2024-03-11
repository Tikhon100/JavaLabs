package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") Long id) {
        Country country = countryService.getCountyById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCountry(@RequestBody Country country) {
        countryService.saveCountry(country);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCountry(@PathVariable("id") Long id,
                                              @RequestBody Country country) {
        countryService.updateCountry(id, country);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
