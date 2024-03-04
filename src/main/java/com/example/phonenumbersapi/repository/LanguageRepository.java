package com.example.phonenumbersapi.repository;

import com.example.phonenumbersapi.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
