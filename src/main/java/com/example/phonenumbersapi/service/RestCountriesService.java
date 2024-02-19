package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.PhoneNumberCode;

public interface RestCountriesService {
    PhoneNumberCode getPhoneNumberCode(String country);
}
