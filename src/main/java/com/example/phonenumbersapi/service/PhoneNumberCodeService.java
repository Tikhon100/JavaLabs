package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneNumberCodeService {
    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private CountryRepository countryRepository;

    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        return phoneNumberCodeRepository.findAll();
    }

    public PhoneNumberCode getPhoneNumberCodeById(Long id) {
        return phoneNumberCodeRepository.findById(id).orElse(null);
    }

    public String createPhoneNumberCode(Long countryId, String code) {
        Country country = countryRepository.findById(countryId).orElse(null);
        if (country == null) {
            return "Wrong id, operation failed";
        } else {
            PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
            phoneNumberCode.setCode(code);
            phoneNumberCode.setCountry(country);

            country.getPhoneNumberCodes().add(phoneNumberCode);

            phoneNumberCodeRepository.save(phoneNumberCode);
            countryRepository.save(country);
            return "Successful!";
        }
    }

    public String updatePhoneNumberCode(Long id, String code) {
        PhoneNumberCode phoneNumberCode = phoneNumberCodeRepository.findById(id).orElse(null);
        if (phoneNumberCode == null) {
            return "Object not found";
        } else {
            if (code != null) {
                phoneNumberCode.setCode(code);
                phoneNumberCodeRepository.save(phoneNumberCode);
            }
            return "Successful!";
        }
    }

    public String deletePhoneNumberCode(Long id) {
        Optional<PhoneNumberCode> optionalPhoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (optionalPhoneNumberCode.isPresent()) {
            phoneNumberCodeRepository.deleteById(id);
            return "Successful";
        } else {
            return "Object with id " + id + " not found";
        }
    }
}
