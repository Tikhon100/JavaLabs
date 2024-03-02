package com.example.phonenumbersapi.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Idd {
    private String root;
    private List<String> suffixes;
}
