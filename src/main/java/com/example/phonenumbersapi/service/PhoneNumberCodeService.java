package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.cashe.RequestCash;
import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PhoneNumberCodeService {
    private static final String ALL_PHONE_NUMBER_CODES_REQUEST = "http://localhost:8080/api/v1/phoneNumberCode/all";
    private static final String PHONE_NUMBER_CODE_BY_ID_REQUEST = "http://localhost:8080/api/v1/language/";

    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private CountryRepository countryRepository;


    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        if (RequestCash.containsKey(ALL_PHONE_NUMBER_CODES_REQUEST)) {

            return (List<PhoneNumberCode>) RequestCash.get(ALL_PHONE_NUMBER_CODES_REQUEST);
        }

        List<PhoneNumberCode> phoneNumberCodes = phoneNumberCodeRepository.findAll();
        RequestCash.put(ALL_PHONE_NUMBER_CODES_REQUEST, phoneNumberCodes);
        return phoneNumberCodes;
    }

    public PhoneNumberCode getPhoneNumberCodeById(final Long id) {
        if (RequestCash.containsKey(PHONE_NUMBER_CODE_BY_ID_REQUEST + id)) {

            return ((List<PhoneNumberCode>) RequestCash.get(PHONE_NUMBER_CODE_BY_ID_REQUEST + id)).get(0);
        } else {
            PhoneNumberCode phoneNumberCode = findPhoneNumberCodeById(id);
            List<PhoneNumberCode> phoneNumberCodeList = new ArrayList<>();
            phoneNumberCodeList.add(phoneNumberCode);
            RequestCash.put(PHONE_NUMBER_CODE_BY_ID_REQUEST + id, phoneNumberCodeList);

            return phoneNumberCode;
        }
    }

    public String createPhoneNumberCode(final Long countryId, final String code) {
        Country country = findCountryById(countryId);
        if (country == null) {
            return "Wrong id, operation failed";
        } else {
            PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
            phoneNumberCode.setCode(code);
            phoneNumberCode.setCountry(country);

            country.getPhoneNumberCodes().add(phoneNumberCode);

            phoneNumberCodeRepository.save(phoneNumberCode);
            countryRepository.save(country);

            RequestCash.clear();

            return "Successful created!";
        }
    }

    public String updatePhoneNumberCode(final Long id, final String code) {
        PhoneNumberCode phoneNumberCode = findPhoneNumberCodeById(id);
        if (phoneNumberCode == null) {
            return "Object not found";
        } else {
            if (code != null) {
                phoneNumberCode.setCode(code);
                phoneNumberCodeRepository.save(phoneNumberCode);
            }
            RequestCash.clear();

            return "Successful updated!";
        }
    }

    public String deletePhoneNumberCode(final Long id) {
        Optional<PhoneNumberCode> optionalPhoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (optionalPhoneNumberCode.isPresent()) {
            phoneNumberCodeRepository.deleteById(id);
            RequestCash.clear();

            return "Successful deleted";
        } else {
            return "Object with id " + id + " not found";
        }
    }

    private Country findCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Country with id: " + id + " not found"));
    }
    private PhoneNumberCode findPhoneNumberCodeById(Long id) {
        return phoneNumberCodeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(("Phone number code with id" + id + "not found")));
    }
}
