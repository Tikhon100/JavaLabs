package com.example.phonenumbersapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Country {
    @Id
    @GeneratedValue(generator = "SEQUENCE")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneNumberCode> phoneNumberCodes;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.EAGER)
    private List<NumberSuffix> numberSuffixes;
}
