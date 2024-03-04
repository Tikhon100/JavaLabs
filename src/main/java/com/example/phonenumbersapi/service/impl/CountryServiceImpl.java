package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.service.CountryService;
import com.example.phonenumbersapi.service.NumberSuffixService;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountyById(Long id) {
        return countryRepository.getReferenceById(id);
    }

    @Override
    public void saveCountry(Country country) {
        country.getLanguages().forEach(language -> language.addCountry(country));
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
        Country country = countryRepository.getReferenceById(id);
        country.getLanguages().forEach(language -> language.removeCountry(country));
        countryRepository.deleteById(id);
    }
}
