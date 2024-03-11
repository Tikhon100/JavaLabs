package com.example.phonenumbersapi.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Language {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "language_country",
            joinColumns = @JoinColumn(name = "language_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )

    private Set<Country> countries = new HashSet<>();

    public void addCountry(Country country) {
        this.countries.add(country);
        country.getLanguages().add(this);
    }

    public void removeCountry(Country country) {
        this.countries.remove(country);
        country.getLanguages().remove(this);
    }
}
