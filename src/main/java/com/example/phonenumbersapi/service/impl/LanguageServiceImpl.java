package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.Language;
import com.example.phonenumbersapi.repository.LanguageRepository;
import com.example.phonenumbersapi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Language getLanguageById(Long id) {
        return languageRepository.getReferenceById(id);
    }

    @Override
    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void updateLanguage(Long id, Language language) {
        language.setId(id);
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(Long id) {
        languageRepository.deleteById(id);
    }
}
