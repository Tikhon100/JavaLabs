package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.aspect.Logged;

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

    private CountryRepository countryRepository;
    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private LanguageRepository languageRepository;

    @Logged
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Logged
    public Country getCountryById(final Long id) {
        return findCountryById(id).get();
    }

    @Logged
    public List<Country> getCountriesByLanguages(final List<String> languages) {

        return countryRepository.getCountriesByLanguages(languages);
    }

    @Logged
    @Transactional
    public String createCountry(final Country country) {
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

        return "Successfully saved!";
    }

    @Logged
    public String updateNameCountry(final Long id, final String name) {
        Optional<Country> country = findCountryById(id);
        if (country.isPresent()) {
            country.get().setName(name);
            countryRepository.save(country.get());

            return "Successful updated!";
        } else {
            return "Error id";
        }
    }

    @Logged
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

    @Logged
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

    @Logged
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

    @Logged
    @Transactional
    public void deleteCountry(final Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
        List<PhoneNumberCode> phoneNumberCodes = new ArrayList<>(country.getPhoneNumberCodes());

        country.getLanguages().forEach(language -> {
            language.getCountries().remove(country);
        });
        country.getLanguages().clear();

        countryRepository.delete(country);

        phoneNumberCodeRepository.deleteAll(phoneNumberCodes);
    }

    private Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    private Optional <Language> findLanguageById(final Long id) {
        return languageRepository.findById(id);
    }

}
