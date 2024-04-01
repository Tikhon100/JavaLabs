package com.example.phonenumbersapi.controller;

import com.example.phonenumbersapi.controller.exception.MyExceptionHandler;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@MyExceptionHandler
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/phoneNumberCode")
public class PhoneNumberCodeController {
    private final PhoneNumberCodeService phoneNumberCodeService;

    @Operation(summary = "Получить все телефонные коды")
    @GetMapping("/all")
    public List<PhoneNumberCode> getAllPhoneNumberCodes() {
        return phoneNumberCodeService.getAllPhoneNumberCodes();
    }

    @Operation(summary = "Получить телефонный код по id")
    @GetMapping("/{id}")
    public PhoneNumberCode getPhoneNumberCodeById(final @PathVariable Long id) {
        return phoneNumberCodeService.getPhoneNumberCodeById(id);
    }

    @Operation(summary = "Добавить телефонный код",
            description = "Запрос добавляет телефонный код к указанной стране")
    @PostMapping("/create/{id}")
    public String createPhoneNumberCode(final @PathVariable Long id, final @RequestParam(required = true) String code) {
        return phoneNumberCodeService.createPhoneNumberCode(id, code);
    }

    @Operation(summary = "Изменить телефоный код")
    @PutMapping("update/{id}")
    public String updatePhoneNumberCode(final @PathVariable Long id, final @RequestParam(required = true) String code) {
        return phoneNumberCodeService.updatePhoneNumberCode(id, code);
    }

    @Operation(summary = "Удалить телефонный код")
    @DeleteMapping("delete/{id}")
    public String deletePhoneNumberCode(final @PathVariable Long id) {
        return phoneNumberCodeService.deletePhoneNumberCode(id);
    }
}
