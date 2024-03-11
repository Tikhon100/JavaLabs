package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();
    Language getLanguageById(Long id);
    void saveLanguage(Language language);
    void updateLanguage(Long id, Language language);
    void deleteLanguage(Long id);
}
