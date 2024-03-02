package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.NumberSuffix;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import com.example.phonenumbersapi.service.CountryService;
import com.example.phonenumbersapi.service.NumberSuffixService;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import com.example.phonenumbersapi.service.RestCountriesService;
import com.example.phonenumbersapi.entity.Idd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberCodeImpl implements PhoneNumberCodeService {

    private final PhoneNumberCodeRepository phoneNumberCodeRepository;

    @Override
    public List<PhoneNumberCode> getAllPhoneNumberCode() {
        return phoneNumberCodeRepository.findAll();
    }

    @Override
    public PhoneNumberCode getPhoneNumberCodeById(Long id) {
        return phoneNumberCodeRepository.getReferenceById(id);
    }

    @Override
    public void savePhoneNumberCode(PhoneNumberCode phoneNumberCode) {
        phoneNumberCodeRepository.save(phoneNumberCode);
    }

    @Override
    public void updatePhoneNumberCode(Long id, PhoneNumberCode phoneNumberCode) {
        phoneNumberCode.setId(id);
        phoneNumberCodeRepository.save(phoneNumberCode);
    }

    @Override
    public void deletePhoneNumberCode(Long id) {
        phoneNumberCodeRepository.deleteById(id);
    }

//    @Override
//    public void createPhoneNumberCodeFromApi(String countryName) {
//        Idd idd = restCountriesService.getPhoneNumberCode(countryName);
//
//        Country country = Country.builder().name(countryName).build();
//        PhoneNumberCode phoneNumberCode = PhoneNumberCode.builder().root(idd.getRoot()).build();
//        List<NumberSuffix> numberSuffixes = idd.getSuffixes().stream()
//                .map(NumberSuffix::new)
//                .toList();
//
//
////        country.setNumberSuffixes(numberSuffixes);
////        countryService.saveCountry(country);
//
//        phoneNumberCode.setSuffixes(numberSuffixes);
//        phoneNumberCode.setCountry(country);
//        phoneNumberCodeRepository.save(phoneNumberCode);
//    }
}
