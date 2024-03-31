package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.aspect.Logged;
import com.example.phonenumbersapi.cashe.RequestCash;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class CountryService {
    private static final String ALL_COUNTRIES_REQUEST = "http://localhost:8080/api/v1/country/all";
    private static final String COUNTRY_BY_ID_REQUEST = "http://localhost:8080/api/v1/country/";

    private CountryRepository countryRepository;
    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private LanguageRepository languageRepository;

    @Logged
    public List<Country> getAllCountries() {
        if (RequestCash.containsKey(ALL_COUNTRIES_REQUEST)) {

            return (List<Country>) RequestCash.get(ALL_COUNTRIES_REQUEST);
        } else {
            List<Country> countryList = countryRepository.findAll();
            RequestCash.put(ALL_COUNTRIES_REQUEST, countryList);

            return countryList;
        }

    }

    @Logged
    public Country getCountryById(Long id) {
        if (RequestCash.containsKey(COUNTRY_BY_ID_REQUEST + id.toString())) {

            return ((List<Country>) RequestCash.get(COUNTRY_BY_ID_REQUEST + id)).get(0);
        } else {

            Country country = findCountryById(id);
            List<Country> countryList = new ArrayList<>();
            countryList.add(country);
            RequestCash.put(COUNTRY_BY_ID_REQUEST + id, countryList);
            return country;
        }
    }

    @Logged
    public List<Country> getCountriesByLanguages(List<String> languages) {

        return countryRepository.getCountriesByLanguages(languages);
    }

    @Logged
    @Transactional
    public String createCountry(Country country) {
       Set<String> languageNames = new HashSet<>();


        for (Language language : country.getLanguages()) {
            if (!languageNames.add(language.getName())) {
                return "Bad request, do not share the same languages in the same country";
            }
        }


        for (PhoneNumberCode phoneNumberCode : country.getPhoneNumberCodes()) {
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

        RequestCash.clear();

        return "Successfully saved!";
    }

    @Logged
    public String updateNameCountry(Long id, String name) {
        Country country = findCountryById(id);
        if (country != null) {
            country.setName(name);
            countryRepository.save(country);

            RequestCash.clear();

            return "Successful updated!";
        } else return "Error id";
    }

    @Logged
    public String addLanguageToCountry(Long countryId, Long languageId) {
        Country country = findCountryById(countryId);
        Language language = findLanguageById(languageId);

        if (country != null && language != null && !country.getLanguages().contains(language)) {
            language.getCountries().add(country);
            country.getLanguages().add(language);
            languageRepository.save(language);
            countryRepository.save(country);

            RequestCash.clear();

            return "Successful added!";
        } else return "Error id or this language already exist in country";
    }

    @Logged
    public String deleteLanguageFromCountry(Long countryId, Long languageId)  {
        Country country = findCountryById(countryId);
        Language language = findLanguageById(languageId);

        if (country != null && language != null && country.getLanguages().contains(language) && language.getCountries().contains(country)) {
            language.getCountries().remove(country);
            country.getLanguages().remove(language);
            languageRepository.save(language);
            countryRepository.save(country);

            RequestCash.clear();
            return "Successful! deleted";
        }
        return "Wrong id or these country do not contains this language";
    }

    @Logged
    public String addPhoneNumberCode(Long id, String code) {
        Country country = findCountryById(id);

        if (country != null) {
            PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
            phoneNumberCode.setCode(code);
            phoneNumberCode.setCountry(country);
            country.getPhoneNumberCodes().add(phoneNumberCode);

            phoneNumberCodeRepository.save(phoneNumberCode);
            countryRepository.save(country);

            RequestCash.clear();
            return "Phone number code successful added!";
        }
        return "Error in id";
    }

    @Logged
    @Transactional
    public void deleteCountry(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
        List<PhoneNumberCode> phoneNumberCodes = new ArrayList<>(country.getPhoneNumberCodes());

        country.getLanguages().forEach(language -> {
            language.getCountries().remove(country);
        });
        country.getLanguages().clear();

        countryRepository.delete(country);

        RequestCash.clear();
        phoneNumberCodeRepository.deleteAll(phoneNumberCodes);
    }

    private Country findCountryById(Long id){
       return countryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Country with id: " + id +" not found"));
    }

    private Language findLanguageById(Long id){
        return languageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Language with id: " + id +" not found"));
    }

}
