package com.example.phonenumbersapi.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PhoneNumberCode phoneNumberCodes;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Language> languages = new HashSet<>();

    public void addLanguage(Language language) {
        this.languages.add(language);
        language.getCountries().add(this);
    }

    private void removeLanguage(Language language) {
        this.languages.remove(language);
        language.getCountries().remove(this);
    }

    public Country() {
        this.languages = new HashSet<>(); // Инициализация коллекции языков в конструкторе
    }
}
