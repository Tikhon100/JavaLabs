package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Idd;


public interface RestCountriesService {
    Idd getPhoneNumberCode(String country);
}
