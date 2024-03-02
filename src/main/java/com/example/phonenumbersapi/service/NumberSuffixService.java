package com.example.phonenumbersapi.service;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.NumberSuffix;

import java.util.List;

public interface NumberSuffixService {
    List<NumberSuffix> getAllNumberSuffix();
    NumberSuffix getNumberSuffixById(Long id);
    void saveNumberSuffix(NumberSuffix numberSuffix);
    void updateNumberSuffix(Long id, NumberSuffix numberSuffix);
    void deleteNumberSuffix(Long id);
}
