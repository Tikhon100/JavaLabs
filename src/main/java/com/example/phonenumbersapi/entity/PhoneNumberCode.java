package com.example.phonenumbersapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
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

    public PhoneNumberCode(String code, Country country) {
        this.code = code;
        this.country = country;
    }
    public PhoneNumberCode(String code) {
        this.code = code;
    }
}
