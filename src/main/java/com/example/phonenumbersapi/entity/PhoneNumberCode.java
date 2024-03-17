package com.example.phonenumbersapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phone_number_codes")
public class PhoneNumberCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @ManyToOne
    @JsonIgnoreProperties(value = {"phoneNumberCodes", "languages"})

    @JoinColumn(name = "country_id")
    private Country country;
}
