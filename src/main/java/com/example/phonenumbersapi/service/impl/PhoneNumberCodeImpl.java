package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;

import com.example.phonenumbersapi.service.PhoneNumberCodeService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

        return phoneNumberCodeRepository.findById(id).orElse(null);
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

}
