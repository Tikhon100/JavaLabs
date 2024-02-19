package com.example.phonenumbersapi.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestCountriesApiResponse {
    private PhoneNumberCode idd;
}
