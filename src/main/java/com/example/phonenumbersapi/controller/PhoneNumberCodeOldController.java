package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.Idd;
import com.example.phonenumbersapi.service.RestCountriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phone-numbers")
@RequiredArgsConstructor
public class PhoneNumberCodeOldController {
    private final RestCountriesService restCountriesService;

    @GetMapping("/get/{country}")
    public ResponseEntity<Idd> getPhoneNumberCodeByCountry(@PathVariable("country") String country) {
        Idd idd = restCountriesService.getPhoneNumberCode(country);
        return ResponseEntity.ok(idd);
    }

    @GetMapping("/save/{country}")
    public ResponseEntity<Idd> savePhoneNumberCodeByCountry(@PathVariable("country") String country) {
        Idd idd = restCountriesService.getPhoneNumberCode(country);
        return ResponseEntity.ok(idd);
    }
}
