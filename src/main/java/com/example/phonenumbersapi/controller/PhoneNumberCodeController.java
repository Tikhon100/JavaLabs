package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@MyExceptionHandler
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/phoneNumberCode")
@CrossOrigin(origins = "*")
public class PhoneNumberCodeController {
    private final PhoneNumberCodeService phoneNumberCodeService;

    @Operation(summary = "Получить все телефонные коды")
    @GetMapping("/all")
    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        return phoneNumberCodeService.getAllPhoneNumberCodes();
    }

    @Operation(summary = "Получить телефонный код по id")
    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumberCode> getPhoneNumberCodeById(final @PathVariable Long id) {
        return phoneNumberCodeService.getPhoneNumberCodeById(id);
    }

    @Operation(summary = "Добавить телефонный код",
            description = "Запрос добавляет телефонный код к указанной стране")
    @PostMapping("/create/{id}")
    public ResponseEntity<String> createPhoneNumberCode(final @PathVariable Long id, final @RequestParam(required = true) String code) {
        return phoneNumberCodeService.createPhoneNumberCode(id, code);
    }

    @Operation(summary = "Изменить телефоный код")
    @PutMapping("update/{id}")
    public ResponseEntity<String> updatePhoneNumberCode(final @PathVariable Long id, final @RequestParam(required = true) String code) {
        return phoneNumberCodeService.updatePhoneNumberCode(id, code);
    }

    @Operation(summary = "Удалить телефонный код")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePhoneNumberCode(final @PathVariable Long id) {
        return phoneNumberCodeService.deletePhoneNumberCode(id);
    }

    @PostMapping("/addListPhoneNumberCodes")
    public String  addPhoneNumberCodesInOneCountry(@RequestBody List<PhoneNumberCode> phoneNumberCodeList, @RequestParam Long countryId) {
        return phoneNumberCodeService.addListPhoneNumberCode(phoneNumberCodeList,countryId);
    }

    @PostMapping("/addListPhoneNumberCodes2")
    public String  addPhoneNumberCodesInDifCountries(@RequestBody List<PhoneNumberCode> phoneNumberCodeList) {
        return phoneNumberCodeService.addListPhoneNumberCode(phoneNumberCodeList);
    }

}
