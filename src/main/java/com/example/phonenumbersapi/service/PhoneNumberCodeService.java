package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.aspect.Counting;
import com.example.phonenumbersapi.entity.Country;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class PhoneNumberCodeService {


    private PhoneNumberCodeRepository phoneNumberCodeRepository;
    private CountryRepository countryRepository;

    @Counting
    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        return phoneNumberCodeRepository.findAll();
    }
    @Counting
    public ResponseEntity<PhoneNumberCode> getPhoneNumberCodeById(final Long id) {
        Optional<PhoneNumberCode> phoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (phoneNumberCode.isPresent()){
            return ResponseEntity.ok().body(phoneNumberCode.get());
        }
        else return ResponseEntity.notFound().build();
    }
    @Counting
    public ResponseEntity<String> createPhoneNumberCode(final Long countryId, final String code) {
        Country country = findCountryById(countryId);
        if (country == null) {
            return ResponseEntity.notFound().build();
        } else {
            PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
            phoneNumberCode.setCode(code);
            phoneNumberCode.setCountry(country);

            country.getPhoneNumberCodes().add(phoneNumberCode);

            phoneNumberCodeRepository.save(phoneNumberCode);
            countryRepository.save(country);


            return ResponseEntity.ok().body("Successfully");
        }
    }
    @Counting
    public ResponseEntity<String> updatePhoneNumberCode(final Long id, final String code) {
        Optional<PhoneNumberCode> phoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (phoneNumberCode.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (code != null) {
                phoneNumberCode.get().setCode(code);
                phoneNumberCodeRepository.save(phoneNumberCode.get());
            }
            return ResponseEntity.ok().body("Succesfull!");
        }
    }
    @Counting
    public ResponseEntity<String> deletePhoneNumberCode(final Long id) {
        Optional<PhoneNumberCode> optionalPhoneNumberCode = phoneNumberCodeRepository.findById(id);
        if (optionalPhoneNumberCode.isPresent()) {
            phoneNumberCodeRepository.deleteById(id);
            return ResponseEntity.ok().body("Successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Counting
    @Transactional
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
    @Counting
    public String addListPhoneNumberCode(List<PhoneNumberCode> phoneNumberCodeList, Long countryId) {
        Country country = findCountryById(countryId);

        if (country == null) {
            return "Country not found!";
        } else {
            phoneNumberCodeList.stream()
                    .filter(phoneNumberCode -> country.getPhoneNumberCodes().stream()
                            .noneMatch(pnc -> pnc.getCode().equals(phoneNumberCode.getCode())))
                    .forEach(phoneNumberCode -> {
                        country.getPhoneNumberCodes().add(phoneNumberCode);
                        phoneNumberCode.setCountry(country);
                        phoneNumberCodeRepository.save(phoneNumberCode);
                    });

            countryRepository.save(country);
            return "Successfully!";
        }
    }


    private Country findCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Country with id: " + id + " not found"));
    }
    private Optional<PhoneNumberCode> findPhoneNumberCodeById(final Long id) {
        return phoneNumberCodeRepository.findById(id);
    }
}
