package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
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

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    public String updateLanguageName(Long id, String name) {
        if (languageRepository.findByName(name)!=null) {
            return "Bad request, language with such name already exist";
        }

        Language language = languageRepository.findById(id).orElse(null);
        if (language == null) {
            return "Error, wrong id";
        } else {
            language.setName(name);
            languageRepository.save(language);
            return "Successful!";
        }
    }


    public String addCountryToLanguage(Long languageId, Long countryId) {
        Language language = languageRepository.findById(languageId).orElse(null);
        Country country = countryRepository.findById(countryId).orElse(null);
        if (language != null && country != null && !language.getCountries().contains(country)) {
            language.getCountries().add(country);
            country.getLanguages().add(language);
            languageRepository.save(language);
            countryRepository.save(country);
            return "Successful!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }

    public String deleteCountryFromLanguage(Long languageId, Long countryId) {
        Language language = languageRepository.findById(languageId).orElse(null);
        Country country = countryRepository.findById(countryId).orElse(null);
        if (language != null && country != null && language.getCountries().contains(country) && country.getLanguages().contains(language)) {
            language.getCountries().remove(country);
            country.getLanguages().remove(language);
            languageRepository.save(language);
            countryRepository.save(country);
            return "Successful!";
        } else return "Wrong at id (it must have been country id or language id)";
    }

    public String createLanguage(String name, List<Long> countryIds) {
        if (languageRepository.findByName(name)!=null) {
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
        return "Successful!";
    }


    public String deleteLanguageById(Long id) {
        Language language = languageRepository.findById(id).orElse(null);

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
}
