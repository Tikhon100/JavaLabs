package com.example.phonenumbersapi.controller;


import com.example.phonenumbersapi.entity.NumberSuffix;

import com.example.phonenumbersapi.service.NumberSuffixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/number-suffixes")
@RequiredArgsConstructor
public class NumberSuffixController {

    private final NumberSuffixService numberSuffixService;

    @GetMapping("/all")
    public ResponseEntity<List<NumberSuffix>> getAllNumberSuffixes() {
        List<NumberSuffix> numberSuffixes = numberSuffixService.getAllNumberSuffix();
        return ResponseEntity.ok(numberSuffixes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NumberSuffix> getNumberSuffixById(@PathVariable("id") Long id) {
        NumberSuffix numberSuffix = numberSuffixService.getNumberSuffixById(id);
        return ResponseEntity.ok(numberSuffix);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createNumberSuffix(@RequestBody NumberSuffix numberSuffix) {
        numberSuffixService.saveNumberSuffix(numberSuffix);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNumberSuffix(@PathVariable("id") Long id,
                                              @RequestBody NumberSuffix numberSuffix) {
        numberSuffixService.updateNumberSuffix(id, numberSuffix);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNumberSuffix(@PathVariable("id") Long id) {
        numberSuffixService.deleteNumberSuffix(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
