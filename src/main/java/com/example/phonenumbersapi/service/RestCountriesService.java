package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Idd;
import com.example.phonenumbersapi.entity.PhoneNumberCode;

public interface RestCountriesService {
    Idd getPhoneNumberCode(String country);
}
