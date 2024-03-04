package com.example.phonenumbersapi.entity;

import jakarta.persistence.*;
import lombok.*;



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
}
