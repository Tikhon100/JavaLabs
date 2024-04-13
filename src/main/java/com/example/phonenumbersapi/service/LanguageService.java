package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class LanguageService {


    private LanguageRepository languageRepository;
    private CountryRepository countryRepository;



    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(final Long id) {
        return findLanguageById(id);
    }

    public String updateLanguageName(final Long id, final String name) {
        if (languageRepository.findByName(name) != null) {
            return "Bad request, language with such name already exist";
        }

        Language language = findLanguageById(id);
        if (language == null) {
            return "Error, wrong id";
        } else {
            language.setName(name);
            languageRepository.save(language);

            return "Successful updated!";
        }
    }


    public String addCountryToLanguage(final Long languageId, final Long countryId) {
        Language language = findLanguageById(languageId);
        Country country = findCountryById(countryId);
        if (language != null && country != null && !language.getCountries().contains(country)) {
            language.getCountries().add(country);
            country.getLanguages().add(language);
            languageRepository.save(language);
            countryRepository.save(country);


            return "Successful added!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }

    public String deleteCountryFromLanguage(final Long languageId, final Long countryId) {
        Language language = findLanguageById(languageId);
        Country country = findCountryById(countryId);
        if (language != null && country != null && language.getCountries().
                contains(country) && country.getLanguages().contains(language)) {
            language.getCountries().remove(country);
            country.getLanguages().remove(language);
            languageRepository.save(language);
            countryRepository.save(country);

            return "Successful deleted!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }

    public String createLanguage(final String name, final List<Long> countryIds) {
        if (languageRepository.findByName(name) != null) {
            return "Bad request, language with such name already exist";
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

        return "Successful created!";
    }


    public String deleteLanguageById(final Long id) {
        Language language = findLanguageById(id);

        if (language != null) {
            List<Country> countries = language.getCountries();

            for (Country country : countries) {
                country.getLanguages().remove(language);
            }
            languageRepository.delete(language);


            return "Successful deleted!";
        } else {
            return "object not found";
        }
    }

    private Country findCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Country with id: " + id + " not found"));
    }

    private Language findLanguageById(final Long id) {
        return languageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Language with id: " + id + " not found"));
    }

}
