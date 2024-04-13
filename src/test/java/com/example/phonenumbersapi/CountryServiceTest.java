package com.example.phonenumbersapi;



import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import com.example.phonenumbersapi.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;
    @Mock
    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    @Mock
    private LanguageRepository languageRepository;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryService(countryRepository, phoneNumberCodeRepository, languageRepository);
    }

    @Test
    void testGetAllCountries() {
        // Arrange
        List<Country> expectedCountries = new ArrayList<>();
        Country country = new Country();
        country.setName("Country 1");
        expectedCountries.add(country);
        country.setName("Country 2");
        expectedCountries.add(country);

        when(countryRepository.findAll()).thenReturn(expectedCountries);

        // Act
        List<Country> result = countryService.getAllCountries();

        // Assert
        assertEquals(expectedCountries, result);
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetCountryByIdExistingId() {
        // Arrange
        Long countryId = 1L;
        Country expectedCountry = new Country("Country 1");

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(expectedCountry));

        // Act
        Country result = countryService.getCountryById(countryId);

        // Assert
        assertEquals(expectedCountry, result);
        verify(countryRepository, times(1)).findById(countryId);
    }

    @Test
    void testDeleteLanguageFromCountry_InvalidIds() {
        // Arrange
        Long countryId = 1L;
        Long languageId = 2L;
        Optional<Country> optionalCountry = Optional.empty();
        Language language = new Language();

        when(countryRepository.findById(countryId)).thenReturn(optionalCountry);
        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));

        // Act
        String result = countryService.deleteLanguageFromCountry(countryId, languageId);

        // Assert
        assertEquals("Wrong id or these country do not contains this language", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }

    @Test
    void testCreateCountryDuplicateLanguages() {

        Country country = new Country("Country 1");
        List<Language> languages = new ArrayList<>();
        languages.add(new Language("Language 1"));
        languages.add(new Language("Language 1"));
        country.setLanguages(languages);

        String result = countryService.createCountry(country);

        assertEquals("Bad request, do not share the same languages in the same country", result);
        verify(countryRepository, never()).save(country);
        verify(languageRepository, never()).save(any(Language.class));
    }

    @Test
    void testGetCountriesByLanguages() {
        // Arrange
        List<String> languages = Arrays.asList("English", "French");

        List<Country> expectedCountries = new ArrayList<>();
        expectedCountries.add(new Country("Country 1"));
        expectedCountries.add(new Country("Country 2"));

        when(countryRepository.getCountriesByLanguages(languages)).thenReturn(expectedCountries);

        // Act
        List<Country> result = countryService.getCountriesByLanguages(languages);

        // Assert
        assertEquals(expectedCountries, result);
        verify(countryRepository, times(1)).getCountriesByLanguages(languages);
    }

    @Test
    void testUpdateNameCountry_ValidId() {
        // Arrange
        Long countryId = 1L;
        String newName = "New Name";

        Country country = new Country("Old Name");
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

        // Act
        String result = countryService.updateNameCountry(countryId, newName);

        // Assert
        assertEquals("Successful updated!", result);
        assertEquals(newName, country.getName());
        verify(countryRepository, times(1)).findById(countryId);
        verify(countryRepository, times(1)).save(country);
    }


    @Test
    void testDeleteCountry_NonExistingCountryId() {
        // Arrange
        Long countryId = 1L;

        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> countryService.deleteCountry(countryId));

        // Assert
        assertEquals("Country not found with id: " + countryId, exception.getMessage());
        verify(countryRepository, times(1)).findById(countryId);
        verify(countryRepository, never()).delete(any());
        verify(phoneNumberCodeRepository, never()).deleteAll(any());
    }

    @Test
    void testDeleteLanguageFromCountry_ValidIds() {
        // Arrange
        Long countryId = 1L;
        Long languageId = 2L;
        Country country = new Country();
        Language language = new Language();
        List<Language> languages = new ArrayList<>();
        languages.add(language);
        List<Country> countries = new ArrayList<>();
        countries.add(country);

        country.setLanguages(languages);
        language.setCountries(countries);

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));

        // Act
        String result = countryService.deleteLanguageFromCountry(countryId, languageId);

        // Assert
        assertEquals("Successful! deleted", result);
        assertFalse(country.getLanguages().contains(language));
        assertFalse(language.getCountries().contains(country));
        verify(countryRepository, times(1)).save(country);
        verify(languageRepository, times(1)).save(language);
    }

    @Test
    void testUpdateNameCountry_ErrorId() {
        // Arrange
        Long countryId = 1L;
        String newName = "New Country Name";
        Optional<Country> optionalCountry = Optional.empty();

        when(countryRepository.findById(countryId)).thenReturn(optionalCountry);

        // Act
        String result = countryService.updateNameCountry(countryId, newName);

        // Assert
        assertEquals("Error id", result);
        verify(countryRepository, never()).save(any());
    }

    @Test
    void testAddPhoneNumberCode_Successful() {
        // Arrange
        Long countryId = 1L;
        String code = "123";
        Optional<Country> optionalCountry = Optional.of(new Country());
        optionalCountry.get().setPhoneNumberCodes(new ArrayList<>()); // Инициализация списка номеров телефонов

        when(countryRepository.findById(countryId)).thenReturn(optionalCountry);

        // Act
        String result = countryService.addPhoneNumberCode(countryId, code);

        // Assert
        assertEquals("Phone number code successfully added!", result);
        verify(phoneNumberCodeRepository).save(any());
        verify(countryRepository).save(any());
    }

    @Test
    void testAddLanguageToCountry_Successful() {
        // Arrange
        Long countryId = 1L;
        Long languageId = 2L;
        Optional<Country> optionalCountry = Optional.of(new Country());
        Optional<Language> optionalLanguage = Optional.of(new Language());

        optionalCountry.get().setLanguages(new ArrayList<>()); // Инициализация списка языков
        optionalLanguage.get().setCountries(new ArrayList<>()); // Инициализация списка стран

        when(countryRepository.findById(countryId)).thenReturn(optionalCountry);
        when(languageRepository.findById(languageId)).thenReturn(optionalLanguage);

        // Act
        String result = countryService.addLanguageToCountry(countryId, languageId);

        // Assert
        assertEquals("Successful added!", result);
        assertTrue(optionalLanguage.get().getCountries().contains(optionalCountry.get()));
        assertTrue(optionalCountry.get().getLanguages().contains(optionalLanguage.get()));
        verify(languageRepository).save(optionalLanguage.get());
        verify(countryRepository).save(optionalCountry.get());
    }



}