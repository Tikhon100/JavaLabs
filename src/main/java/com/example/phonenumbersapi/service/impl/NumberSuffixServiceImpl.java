package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.NumberSuffix;
import com.example.phonenumbersapi.repository.NumberSuffixRepository;
import com.example.phonenumbersapi.service.NumberSuffixService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NumberSuffixServiceImpl implements NumberSuffixService {

    private final NumberSuffixRepository numberSuffixRepository;

    @Override
    public List<NumberSuffix> getAllNumberSuffix() {
        return numberSuffixRepository.findAll();
    }

    @Override
    public NumberSuffix getNumberSuffixById(Long id) {
        return numberSuffixRepository.getReferenceById(id);
    }

    @Override
    public void saveNumberSuffix(NumberSuffix numberSuffix) {
        numberSuffixRepository.save(numberSuffix);
    }

    @Override
    public void updateNumberSuffix(Long id, NumberSuffix numberSuffix) {
        numberSuffix.setId(id);
        numberSuffixRepository.save(numberSuffix);
    }

    @Override
    public void deleteNumberSuffix(Long id) {
        numberSuffixRepository.deleteById(id);
    }
}
