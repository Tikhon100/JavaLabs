package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.service.RestCountriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phone-number")
@RequiredArgsConstructor
public class PhoneNumberCodeController {

    private final RestCountriesService restCountriesService;

    @GetMapping("/country/{country}")
    public ResponseEntity<PhoneNumberCode> getPhoneNumberCodeByCountry(@PathVariable("country") String country) {
        PhoneNumberCode phoneNumberCode = restCountriesService.getPhoneNumberCode(country);

        return ResponseEntity.ok(phoneNumberCode);
    }
}
