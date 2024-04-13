package com.example.phonenumbersapi;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanguageServiceTest {
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private LanguageService languageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLanguages() {
        // Arrange
        List<Language> expectedLanguages = Arrays.asList(
                new Language(1L, "English"),
                new Language(2L, "Spanish"),
                new Language(3L, "French")
        );
        when(languageRepository.findAll()).thenReturn(expectedLanguages);

        // Act
        List<Language> actualLanguages = languageService.getAllLanguages();

        // Assert
        assertEquals(expectedLanguages, actualLanguages);
        verify(languageRepository).findAll();
    }

    @Test
    void testGetLanguageById_ExistingId() {
        // Arrange
        Long id = 1L;
        Language expectedLanguage = new Language(id, "English");
        when(languageRepository.findById(id)).thenReturn(Optional.of(expectedLanguage));

        // Act
        Language actualLanguage = languageService.getLanguageById(id);

        // Assert
        assertEquals(expectedLanguage, actualLanguage);
        verify(languageRepository).findById(id);
    }

    @Test
    void testGetLanguageById_NonExistingId() {
        // Arrange
        Long id = 1L;
        when(languageRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Language actualLanguage = languageService.getLanguageById(id);

        // Assert
        assertNull(actualLanguage);
        verify(languageRepository).findById(id);
    }

    @Test
    void testUpdateLanguageName_ValidIdAndUniqueName() {
        // Arrange
        Long id = 1L;
        String newName = "German";
        Language language = new Language(id, "English");
        when(languageRepository.findById(id)).thenReturn(Optional.of(language));
        when(languageRepository.findByName(newName)).thenReturn(null);

        // Act
        String result = languageService.updateLanguageName(id, newName);

        // Assert
        assertEquals("Successful updated!", result);
        assertEquals(newName, language.getName());
        verify(languageRepository).findById(id);
        verify(languageRepository).findByName(newName);
        verify(languageRepository).save(language);
    }

    @Test
    void testAddCountryToLanguage_InvalidLanguageId() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        when(languageRepository.findById(languageId)).thenReturn(Optional.empty());
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(new Country(countryId, "United States")));

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any(Language.class));
        verify(countryRepository, never()).save(any(Country.class));
    }

    @Test
    void testAddCountryToLanguage_InvalidCountryId() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        when(languageRepository.findById(languageId)).thenReturn(Optional.of(new Language(languageId, "English")));
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any(Language.class));
        verify(countryRepository, never()).save(any(Country.class));
    }


    @Test
    void addCountryToLanguage_ShouldReturnWrongId_WhenInvalidLanguageIdProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;

        when(languageRepository.findById(languageId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);
        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }

    @Test
    void addCountryToLanguage_ShouldReturnWrongId_WhenInvalidCountryIdProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }




    @Test
    void addCountryToLanguage_ShouldReturnSuccessfulAdded_WhenValidLanguageAndCountryIdsProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");
        Country country = new Country(countryId, "USA");

        language.setCountries(new ArrayList<>()); // Инициализация списка countries
        country.setLanguages(new ArrayList<>()); // Инициализация списка languages

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
        when(languageRepository.save(language)).thenReturn(language);
        when(countryRepository.save(country)).thenReturn(country);

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);

        // Assert
        assertEquals("Successful added!", result);
        verify(languageRepository, times(1)).save(language);
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void addCountryToLanguage_ShouldReturnWrongId_WhenCountryAlreadyAssociatedWithLanguage() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");
        Country country = new Country(countryId, "USA");

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        language.setCountries(countries); // Инициализация списка countries

        List<Language> languages = new ArrayList<>();
        languages.add(language);
        country.setLanguages(languages); // Инициализация списка languages

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

        // Act
        String result = languageService.addCountryToLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }

    @Test
    void deleteCountryFromLanguage_ShouldReturnSuccessfulDeleted_WhenValidLanguageAndCountryIdsProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");
        Country country = new Country(countryId, "USA");

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        language.setCountries(countries);

        List<Language> languages = new ArrayList<>();
        languages.add(language);
        country.setLanguages(languages);

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
        when(languageRepository.save(language)).thenReturn(language);
        when(countryRepository.save(country)).thenReturn(country);

        // Act
        String result = languageService.deleteCountryFromLanguage(languageId, countryId);

        // Assert
        assertEquals("Successful deleted!", result);
        assertEquals(0, language.getCountries().size());
        assertEquals(0, country.getLanguages().size());
        verify(languageRepository, times(1)).save(language);
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void deleteCountryFromLanguage_ShouldReturnWrongId_WhenInvalidLanguageIdProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;

        when(languageRepository.findById(languageId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.deleteCountryFromLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }

    @Test
    void deleteCountryFromLanguage_ShouldReturnWrongId_WhenInvalidCountryIdProvided() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.deleteCountryFromLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }



    @Test
    void deleteCountryFromLanguage_ShouldReturnWrongId_WhenCountryNotAssociatedWithLanguage() {
        // Arrange
        Long languageId = 1L;
        Long countryId = 2L;
        Language language = new Language(languageId, "English");
        Country country = new Country(countryId, "USA");

        language.setCountries(new ArrayList<>()); // Инициализация списка countries

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

        // Act
        String result = languageService.deleteCountryFromLanguage(languageId, countryId);

        // Assert
        assertEquals("Wrong at id (it must have been country id or language id)", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).save(any());
    }


    @Test
    void createLanguage_ShouldReturnSuccessfulCreated_WhenValidNameAndNoCountryIdsProvided() {
        // Arrange
        String name = "English";

        when(languageRepository.findByName(name)).thenReturn(null);

        // Act
        String result = languageService.createLanguage(name, null);

        // Assert
        assertEquals("Successful created!", result);
        verify(languageRepository, times(1)).save(any());
        verify(countryRepository, never()).findAllById(any());
        verify(countryRepository, never()).save(any());
    }


    @Test
    void createLanguage_ShouldReturnBadRequest_WhenLanguageWithSameNameAlreadyExists() {
        // Arrange
        String name = "English";

        Language existingLanguage = new Language();
        existingLanguage.setName(name);

        when(languageRepository.findByName(name)).thenReturn(existingLanguage);

        // Act
        String result = languageService.createLanguage(name, null);

        // Assert
        assertEquals("Bad request, language with such name already exist", result);
        verify(languageRepository, never()).save(any());
        verify(countryRepository, never()).findAllById(any());
        verify(countryRepository, never()).save(any());
    }


    @Test
    void deleteLanguageById_ShouldReturnObjectNotFound_WhenLanguageDoesNotExist() {
        // Arrange
        Long languageId = 1L;

        when(languageRepository.findById(languageId)).thenReturn(Optional.empty());

        // Act
        String result = languageService.deleteLanguageById(languageId);

        // Assert
        assertEquals("object not found", result);
        verify(languageRepository, never()).delete(any());
    }



}