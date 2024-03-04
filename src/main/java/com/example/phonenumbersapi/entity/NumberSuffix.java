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

    public NumberSuffix(String number) {
        this.number = Integer.parseInt(number);
    }
}
