package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.NumberSuffix;
import com.example.phonenumbersapi.entity.PhoneNumberCode;

import java.util.List;

public interface PhoneNumberCodeService {
    List<PhoneNumberCode> getAllPhoneNumberCode();
    PhoneNumberCode getPhoneNumberCodeById(Long id);
    void savePhoneNumberCode(PhoneNumberCode phoneNumberCode);
    void updatePhoneNumberCode(Long id, PhoneNumberCode phoneNumberCode);
    void deletePhoneNumberCode(Long id);
//    void createPhoneNumberCodeFromApi(String country);
}
