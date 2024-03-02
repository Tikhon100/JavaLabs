package com.example.phonenumbersapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NumberSuffix {
    @Id
    @GeneratedValue(generator = "SEQUENCE")
    private Long id;

    private Integer number;

    @ManyToOne
    private PhoneNumberCode phoneNumberCode;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "number_suffix_country",
            joinColumns = @JoinColumn(name = "number_suffix_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    public NumberSuffix(String number) {
        this.number = Integer.parseInt(number);
    }
}
