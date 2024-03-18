package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.cashe.RequestCash;
import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class PhoneNumberCodeService {
    private static final String ALL_PHONE_NUMBER_CODES_REQUEST = "http://localhost:8080/api/v1/phoneNumberCode/all";
    private static final String PHONE_NUMBER_CODE_BY_ID_REQUEST = "http://localhost:8080/api/v1/language/";

    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private CountryRepository countryRepository;

    private static final Logger LOGGER = Logger.getLogger(PhoneNumberCodeService.class.getName());

    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        if (RequestCash.containsKey(ALL_PHONE_NUMBER_CODES_REQUEST)) {
            LOGGER.info("Getting all phone number code from cache");
            return (List<PhoneNumberCode>) RequestCash.get(ALL_PHONE_NUMBER_CODES_REQUEST);
        }
        LOGGER.info("Getting data from DB");
        List<PhoneNumberCode> phoneNumberCodes = phoneNumberCodeRepository.findAll();
        RequestCash.put(ALL_PHONE_NUMBER_CODES_REQUEST, phoneNumberCodes);
        return phoneNumberCodes;
    }

    public PhoneNumberCode getPhoneNumberCodeById(Long id) {
        if (RequestCash.containsKey(PHONE_NUMBER_CODE_BY_ID_REQUEST + id)) {
            LOGGER.info("Getting phone number code by id from cache");
            return ((List<PhoneNumberCode>) RequestCash.get(PHONE_NUMBER_CODE_BY_ID_REQUEST + id)).get(0);
        } else {
            PhoneNumberCode phoneNumberCode = phoneNumberCodeRepository.findById(id).orElse(null);
            List<PhoneNumberCode> phoneNumberCodeList = new ArrayList<>();
            phoneNumberCodeList.add(phoneNumberCode);
            RequestCash.put(PHONE_NUMBER_CODE_BY_ID_REQUEST + id, phoneNumberCodeList);
            LOGGER.info("Getting phone number code by id from DB");
            return phoneNumberCode;
        }
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

            RequestCash.clear();
            LOGGER.info("Cache cleared in function createPhoneNumberCode");
            return "Successful created!";
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
            RequestCash.clear();
            LOGGER.info("Cache cleared in function updatePhoneNumberCode");
            return "Successful updated!";
        }
    }

    public String deletePhoneNumberCode(Long id) {
        Optional<PhoneNumberCode> optionalPhoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (optionalPhoneNumberCode.isPresent()) {
            phoneNumberCodeRepository.deleteById(id);
            RequestCash.clear();
            LOGGER.info("Cache cleared in function deletePhoneNumberCode");
            return "Successful deleted";
        } else {
            return "Object with id " + id + " not found";
        }
    }
}
