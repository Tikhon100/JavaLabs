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
    @GeneratedValue
    private Long id;

    private Integer number;

    @ManyToOne (cascade = CascadeType.ALL)
    private PhoneNumberCode phoneNumberCode;
    public NumberSuffix(String number) {
        this.number = Integer.parseInt(number);
    }
}
