package com.example.phonenumbersapi.repository;

import com.example.phonenumbersapi.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CountryRepository extends JpaRepository<Country, Long> {


    @Query("SELECT DISTINCT c FROM Country c JOIN c.languages l WHERE l.name IN :languages")
    List<Country> getCountriesByLanguages(List<String> languages);
}