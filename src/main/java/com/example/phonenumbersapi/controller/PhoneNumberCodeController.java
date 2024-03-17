package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/phoneNumberCode")
public class PhoneNumberCodeController {
    private final PhoneNumberCodeService phoneNumberCodeService;

    @GetMapping("/all")
    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        return phoneNumberCodeService.getAllPhoneNumberCodes();
    }

    @GetMapping("/{id}")
    public PhoneNumberCode getPhoneNumberCodeById(@PathVariable Long id) {
        return phoneNumberCodeService.getPhoneNumberCodeById(id);
    }

    @PostMapping("/create/{id}")
    public String createPhoneNumberCode(@PathVariable Long id, @RequestParam(required = true) String code) {
        return phoneNumberCodeService.createPhoneNumberCode(id, code);
    }


    @PutMapping("update/{id}")
    public String updatePhoneNumberCode(@PathVariable Long id, @RequestParam(required = true) String code) {
        return phoneNumberCodeService.updatePhoneNumberCode(id, code);
    }

    @DeleteMapping("delete/{id}")
    public String deletePhoneNumberCode(@PathVariable Long id) {
        return phoneNumberCodeService.deletePhoneNumberCode(id);
    }
}
