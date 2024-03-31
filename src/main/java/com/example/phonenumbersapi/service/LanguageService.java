package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.cashe.RequestCash;
import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class LanguageService {

    private static final String ALL_LANGUAGES_REQUEST = "http://localhost:8080/api/v1/language/all";
    private static final String LANGUAGE_BY_ID_REQUEST = "http://localhost:8080/api/v1/language/";

    private LanguageRepository languageRepository;
    private CountryRepository countryRepository;



    public List<Language> getAllLanguages() {
        if (RequestCash.containsKey(ALL_LANGUAGES_REQUEST)) {

            return (List<Language>) RequestCash.get(ALL_LANGUAGES_REQUEST);
        } else {

            List<Language> languageList = languageRepository.findAll();
            RequestCash.put(ALL_LANGUAGES_REQUEST, languageList);

            return languageList;
        }
    }

    public Language getLanguageById(Long id) {

        if (RequestCash.containsKey(LANGUAGE_BY_ID_REQUEST + id)) {

            return ((List<Language>) RequestCash.get(LANGUAGE_BY_ID_REQUEST + id)).get(0);
        } else {

            Language language = findLanguageById(id);
            List<Language> languageList = new ArrayList<>();
            languageList.add(language);
            RequestCash.put(LANGUAGE_BY_ID_REQUEST + id, languageList);
            return language;
        }
    }

    public String updateLanguageName(Long id, String name) {
        if (languageRepository.findByName(name) != null) {
            return "Bad request, language with such name already exist";
        }

        Language language = findLanguageById(id);
        if (language == null) {
            return "Error, wrong id";
        } else {
            language.setName(name);
            languageRepository.save(language);

            RequestCash.clear();

            return "Successful updated!";
        }
    }


    public String addCountryToLanguage(Long languageId, Long countryId) {
        Language language = findLanguageById(languageId);
        Country country = findCountryById(countryId);
        if (language != null && country != null && !language.getCountries().contains(country)) {
            language.getCountries().add(country);
            country.getLanguages().add(language);
            languageRepository.save(language);
            countryRepository.save(country);

            RequestCash.clear();

            return "Successful added!";
        } else {
            return "Wrong at id (it must have been country id or language id)";
        }
    }

    public String deleteCountryFromLanguage(Long languageId, Long countryId) {
        Language language = findLanguageById(languageId);
        Country country = findCountryById(countryId);
        if (language != null && country != null && language.getCountries().contains(country) && country.getLanguages().contains(language)) {
            language.getCountries().remove(country);
            country.getLanguages().remove(language);
            languageRepository.save(language);
            countryRepository.save(country);
            RequestCash.clear();

            return "Successful deleted!";
        } else return "Wrong at id (it must have been country id or language id)";
    }

    public String createLanguage(String name, List<Long> countryIds) {
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
        RequestCash.clear();

        return "Successful created!";
    }


    public String deleteLanguageById(Long id) {
        Language language = findLanguageById(id);

        if (language != null) {
            List<Country> countries = language.getCountries();

            for (Country country : countries) {
                country.getLanguages().remove(language);
            }
            languageRepository.delete(language);

            RequestCash.clear();

            return "Successful deleted!";
        } else {
            return "object not found";
        }
    }

    private Country findCountryById(Long id){
        return countryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Country with id: " + id +" not found"));
    }

    private Language findLanguageById(Long id){
        return languageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Language with id: " + id +" not found"));
    }

}
