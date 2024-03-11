package com.example.phonenumbersapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PhoneNumberCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String root;

    @OneToMany(mappedBy = "phoneNumberCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NumberSuffix> suffixes;

    @OneToOne
    @JsonIgnore
    private Country country;
}
