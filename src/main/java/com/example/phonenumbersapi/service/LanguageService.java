package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.aspect.Counting;
import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class LanguageService {


    private LanguageRepository languageRepository;
    private CountryRepository countryRepository;


    @Counting
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
    @Counting
    public ResponseEntity<Language> getLanguageById(final Long id) {
        Optional<Language> language = findLanguageById(id);
        if (language.isPresent()){
            return ResponseEntity.ok().body(language.get());
        }
        return ResponseEntity.notFound().build();
    }
    @Counting
    public ResponseEntity<String> updateLanguageName(final Long id, final String name) {
        if (languageRepository.findByName(name) != null) {
            return ResponseEntity.badRequest().body("Language with such name already exist");
        }

        Optional<Language> language = findLanguageById(id);
        if (language.isEmpty()) {
            return ResponseEntity.badRequest().body("Error, wrong id");
        } else {
            language.get().setName(name);
            languageRepository.save(language.get());

            return ResponseEntity.ok().body("Successfully");
        }
    }

    @Counting
    public String addCountryToLanguage(final Long languageId, final Long countryId) {
        Optional<Language> language = findLanguageById(languageId);
        Optional<Country> country = findCountryById(countryId);
        if (language.isPresent() && country.isPresent() && !language.get().getCountries().contains(country.get())) {
            language.get().getCountries().add(country.get());
            country.get().getLanguages().add(language.get());
            languageRepository.save(language.get());
            countryRepository.save(country.get());


            return "Successful added!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }
    @Counting
    public String deleteCountryFromLanguage(final Long languageId, final Long countryId) {
        Optional<Language> language = findLanguageById(languageId);
        Optional<Country> country = findCountryById(countryId);
        if (language.isPresent() && country.isPresent() && language.get().getCountries().
                contains(country.get()) && country.get().getLanguages().contains(language.get())) {
            language.get().getCountries().remove(country.get());
            country.get().getLanguages().remove(language.get());
            languageRepository.save(language.get());
            countryRepository.save(country.get());

            return "Successful deleted!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }
    @Counting
    public ResponseEntity<String> createLanguage(final String name, final List<Long> countryIds) {
        if (languageRepository.findByName(name) != null) {
            ResponseEntity.badRequest().body("Язык уже есть");
        }

        Language newLanguage = new Language();
        newLanguage.setName(name);
        if (countryIds == null || countryIds.isEmpty()) {
            newLanguage.setCountries(new ArrayList<>());
            languageRepository.save(newLanguage);
        } else {
            newLanguage.setCountries(countryRepository.findAllById(countryIds));
            languageRepository.save(newLanguage);
            for (Country country : newLanguage.getCountries()) {
                country.getLanguages().add(newLanguage);
                countryRepository.save(country);
            }
        }

        return ResponseEntity.ok().body("Успешно");
    }

    @Counting
    public ResponseEntity<String> deleteLanguageById(final Long id) {
        Optional<Language> language = findLanguageById(id);

        if (language.isPresent()) {
            List<Country> countries = language.get().getCountries();

            for (Country country : countries) {
                country.getLanguages().remove(language.get());
            }
            languageRepository.delete(language.get());


            return ResponseEntity.ok().body("Успешно");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Optional<Country> findCountryById(final Long id) {
        return countryRepository.findById(id);
    }

    private Optional<Language> findLanguageById(final Long id) {
        return languageRepository.findById(id);
    }

}
