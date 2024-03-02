package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
    Country getCountyById(Long id);
    void saveCountry(Country country);
    void updateCountry(Long id, Country country);
    void deleteCountry(Long id);
}
