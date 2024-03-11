package com.example.phonenumbersapi.controller;


import com.example.phonenumbersapi.entity.PhoneNumberCode;

import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phone-number-codes")
@RequiredArgsConstructor
public class PhoneNumberCodeController {

    private final PhoneNumberCodeService phoneNumberCodeService;

    @GetMapping("/all")
    public ResponseEntity<List<PhoneNumberCode>> getAllPhoneNumberCodes() {
        List<PhoneNumberCode> phoneNumberCode = phoneNumberCodeService.getAllPhoneNumberCode();
        return ResponseEntity.ok(phoneNumberCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumberCode> getPhoneNumberCodeById(@PathVariable("id") Long id) {
        PhoneNumberCode phoneNumberCode = phoneNumberCodeService.getPhoneNumberCodeById(id);
        return ResponseEntity.ok(phoneNumberCode);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createPhoneNumberCode(@RequestBody PhoneNumberCode phoneNumberCode) {
        phoneNumberCodeService.savePhoneNumberCode(phoneNumberCode);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePhoneNumberCode(@PathVariable("id") Long id,
                                                      @RequestBody PhoneNumberCode phoneNumberCode) {
        phoneNumberCodeService.updatePhoneNumberCode(id, phoneNumberCode);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoneNumberCode(@PathVariable("id") Long id) {
        phoneNumberCodeService.deletePhoneNumberCode(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
