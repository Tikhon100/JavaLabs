package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

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

    public PhoneNumberCode getPhoneNumberCodeById(final Long id) {
        return findPhoneNumberCodeById(id);
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

            return "Successful updated!";
        }
    }

    public String deletePhoneNumberCode(final Long id) {
        Optional<PhoneNumberCode> optionalPhoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (optionalPhoneNumberCode.isPresent()) {
            phoneNumberCodeRepository.deleteById(id);

            return "Successful deleted";
        } else {
            return "Object with id " + id + " not found";
        }
    }

    public String addListPhoneNumberCode(List<PhoneNumberCode> phoneNumberCodeList) {
        phoneNumberCodeList.stream()
                .forEach(phoneNumberCode -> {
                    Country country = countryRepository.findById(phoneNumberCode.getCountry().getId()).
                            orElseThrow(() -> new IllegalArgumentException("Country not found"));
                    List<PhoneNumberCode> countryCodeList = country.getPhoneNumberCodes();
                    int flag=0;
                    for (PhoneNumberCode pnc : countryCodeList){
                        if (pnc.getCode().equals(phoneNumberCode.getCode())) {
                            flag = 1;
                            break;
                        }
                    }

                    if ((flag!=1)){
                        country.getPhoneNumberCodes().add(phoneNumberCode);
                        phoneNumberCodeRepository.save(phoneNumberCode);
                        countryRepository.save(country);
                    }
                });
        return "Successfully";
    }

    public String addListPhoneNumberCode(List<PhoneNumberCode> phoneNumberCodeList, Long countryId) {
        Country country = findCountryById(countryId);

        if (country == null) {
            return "Country not found!";
        } else {
            phoneNumberCodeList.stream()
                    .forEach(phoneNumberCode -> {
                        List<PhoneNumberCode> countryCodeList = country.getPhoneNumberCodes();
                        int flag=0;
                        for (PhoneNumberCode pnc : countryCodeList){
                            if (pnc.getCode().equals(phoneNumberCode.getCode())) {
                                flag = 1;
                                break;
                            }
                        }

                        if (flag!=1){
                            country.getPhoneNumberCodes().add(phoneNumberCode);
                            phoneNumberCode.setCountry(country);
                            phoneNumberCodeRepository.save(phoneNumberCode);
                        }
                    });

            countryRepository.save(country);
            return "Successfully!";
        }
    }


    private Country findCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Country with id: " + id + " not found"));
    }
    private PhoneNumberCode findPhoneNumberCodeById(final Long id) {
        return phoneNumberCodeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(("Phone number code with id" + id + "not found")));
    }
}
