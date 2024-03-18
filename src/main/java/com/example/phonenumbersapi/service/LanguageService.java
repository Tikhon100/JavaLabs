package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.cashe.RequestCash;
import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
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

    private static final Logger LOGGER = Logger.getLogger(LanguageService.class.getName());

    public List<Language> getAllLanguages() {
        if (RequestCash.containsKey(ALL_LANGUAGES_REQUEST)) {
            LOGGER.info("Getting all languages from cache");
            return (List<Language>) RequestCash.get(ALL_LANGUAGES_REQUEST);
        } else {

            List<Language> languageList = languageRepository.findAll();
            RequestCash.put(ALL_LANGUAGES_REQUEST, languageList);
            LOGGER.info("Getting all languages form DB");
            return languageList;
        }
    }

    public Language getLanguageById(Long id) {

        if (RequestCash.containsKey(LANGUAGE_BY_ID_REQUEST + id)) {
            LOGGER.info("Getting language by id from cache");
            return ((List<Language>) RequestCash.get(LANGUAGE_BY_ID_REQUEST + id)).get(0);
        } else {
            LOGGER.info("Getting language by id from DB");
            Language language = languageRepository.findById(id).orElse(null);
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

        Language language = languageRepository.findById(id).orElse(null);
        if (language == null) {
            return "Error, wrong id";
        } else {
            language.setName(name);
            languageRepository.save(language);

            if (RequestCash.containsKey(ALL_LANGUAGES_REQUEST)) {
                RequestCash.remove(ALL_LANGUAGES_REQUEST);
            }
            if (RequestCash.containsKey(LANGUAGE_BY_ID_REQUEST + id)) {
                RequestCash.remove(LANGUAGE_BY_ID_REQUEST + id);
            }
            LOGGER.info("Part of data deleted from cache");
            return "Successful updated!";
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

            RequestCash.clear();
            LOGGER.info("Cache cleared in func: addCountryToLanguage");
            return "Successful added!";
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
            RequestCash.clear();
            LOGGER.info("Cache cleared in func: deleteCountryFromLanguage");
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
        LOGGER.info("Cache cleared in func: createLanguage");
        return "Successful created!";
    }


    public String deleteLanguageById(Long id) {
        Language language = languageRepository.findById(id).orElse(null);

        if (language != null) {
            List<Country> countries = language.getCountries();

            for (Country country : countries) {
                country.getLanguages().remove(language);
            }
            languageRepository.delete(language);

            if (RequestCash.containsKey(ALL_LANGUAGES_REQUEST)) {
                RequestCash.remove(ALL_LANGUAGES_REQUEST);
            }
            if (RequestCash.containsKey(LANGUAGE_BY_ID_REQUEST + id)) {
                RequestCash.remove(LANGUAGE_BY_ID_REQUEST + id);
            }
            LOGGER.info("All languages response and language by id response deleted if there are in cache");
            return "Successful deleted!";
        } else {
            return "object not found";
        }
    }
}
