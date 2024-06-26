package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.aspect.Counting;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class CountryService {

    private CountryRepository countryRepository;
    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private LanguageRepository languageRepository;


    @Counting
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Counting
    public Country getCountryById(final Long id) {
        Optional <Country> country = findCountryById(id);
        return country.orElse(null);
    }
    @Counting
    public List<Country> getCountriesByLanguages(final List<String> languages) {

        return countryRepository.getCountriesByLanguages(languages);
    }

    @Counting
    @Transactional
    public String createCountry(final Country country) {
       Set<String> languageNames = new HashSet<>();


        for (Language language : country.getLanguages()) {
            if (!languageNames.add(language.getName())) {
                return "Bad request, do not share the same languages in the same country";
            }
        }


        List<PhoneNumberCode> phoneNumberCodes = country.getPhoneNumberCodes();
        for (PhoneNumberCode phoneNumberCode : phoneNumberCodes) {
            phoneNumberCode.setCountry(country);
        }

        List<Language> languages = country.getLanguages();
        List<Language> managedLanguages = new ArrayList<>();

        for (Language language : languages) {
            Language existingLanguage = languageRepository.findByName(language.getName());

            if (existingLanguage != null) {
                // Если язык уже существует, используем существующий
                managedLanguages.add(existingLanguage);
            } else {
                // Если язык не существует, сохраняем новый язык в базу данных
                languageRepository.save(language);
                managedLanguages.add(language);
            }
        }

        // Обновляем список языков в стране
        country.setLanguages(managedLanguages);

        // Сохраняем страну в базу данных
        countryRepository.save(country);

        return "Successfully saved!";
    }

    @Counting
    public ResponseEntity<Country> updateNameCountry(final Long id, final String name) {
        Optional<Country> country = findCountryById(id);
        if (country.isPresent()) {
            country.get().setName(name);
            countryRepository.save(country.get());

            return ResponseEntity.ok().body(country.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Counting
    public String addLanguageToCountry(final Long countryId, final Long languageId) {
        Optional<Country> country = findCountryById(countryId);
        Optional<Language> language = findLanguageById(languageId);

        if (country.isPresent() && language.isPresent()  && !country.get().getLanguages().contains(language.get())) {
            language.get().getCountries().add(country.get());
            country.get().getLanguages().add(language.get());
            languageRepository.save(language.get());
            countryRepository.save(country.get());

            return "Successful added!";
        } else {
            return "Error id or this language already exist in country";
        }
    }

    @Counting
    public String deleteLanguageFromCountry(final Long countryId, final Long languageId)  {
        Optional<Country> country = findCountryById(countryId);
        Optional<Language> language = findLanguageById(languageId);

        if (country.isPresent() && language.isPresent() && country.get().getLanguages().contains(language.get())
                && language.get().getCountries().contains(country.get())) {
            language.get().getCountries().remove(country.get());
            country.get().getLanguages().remove(language.get());
            languageRepository.save(language.get());
            countryRepository.save(country.get());

            return "Successful! deleted";
        }
        return "Wrong id or these country do not contains this language";
    }

    @Counting
    public String addPhoneNumberCode(final Long id, final String code) {
        Optional<Country> optionalCountry = findCountryById(id);

        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();
            PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
            phoneNumberCode.setCode(code);
            phoneNumberCode.setCountry(country);
            country.getPhoneNumberCodes().add(phoneNumberCode);

            phoneNumberCodeRepository.save(phoneNumberCode);
            countryRepository.save(country);

            return "Phone number code successfully added!";
        }

        return "Error in id";
    }

    @Counting
    @Transactional
    public ResponseEntity<String> deleteCountry(final Long countryId) {
        Optional<Country> optionalCountry = findCountryById(countryId);
        if (optionalCountry.isPresent()){
            Country country = optionalCountry.get();
            List<PhoneNumberCode> phoneNumberCodes = new ArrayList<>(country.getPhoneNumberCodes());
            country.getLanguages().forEach(language ->
                    language.getCountries().remove(country)
            );
            country.getLanguages().clear();
            countryRepository.delete(country);
            phoneNumberCodeRepository.deleteAll(phoneNumberCodes);
            return ResponseEntity.ok().body("Successfully delete");
        }
        return ResponseEntity.notFound().build();
    }
    private Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    private Optional <Language> findLanguageById(final Long id) {
        return languageRepository.findById(id);
    }
}
