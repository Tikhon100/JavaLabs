package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.service.CountryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;



@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final LanguageRepository languageRepository;
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountyById(Long id) {
        return  countryRepository.getReferenceById(id);
    }

    @Override
    public void saveCountry(Country country) {
        country.getLanguages().forEach(country::addLanguage);
        country.getPhoneNumberCodes().setCountry(country);
        countryRepository.save(country);
    }

    @Override
    public void updateCountry(Long id, Country country) {
        country.setId(id);
        countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Long id) {
        Country country = this.getCountyById(id);
        System.out.println(country.getName());
        for (Language language : new ArrayList<>(country.getLanguages())){
            language.removeCountry(country);
        }
        countryRepository.deleteById(id);
    }
}
