package com.example.phonenumbersapi.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhoneNumberCode {
    private String root;
    private List<String> suffixes;
}
