package com.example.phonenumbersapi.repository;

import com.example.phonenumbersapi.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("SELECT l FROM Language l WHERE l.name = :name")
    Language findByName(String name);

}
