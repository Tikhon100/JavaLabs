package com.example.phonenumbersapi;

import com.example.phonenumbersapi.entity.Country;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.repository.CountryRepository;
import com.example.phonenumbersapi.repository.PhoneNumberCodeRepository;
import com.example.phonenumbersapi.service.PhoneNumberCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneNumberCodeServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private PhoneNumberCodeRepository phoneNumberCodeRepository;

    @InjectMocks
    private PhoneNumberCodeService phoneNumberCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPhoneNumberCode_WithValidCountryIdAndCode_ShouldReturnSuccessfulMessage() {
        Long countryId = 1L;
        String code = "123";

        Country country = new Country();
        country.setId(countryId);
        List<PhoneNumberCode> phoneNumberCodes = new ArrayList<>();
        country.setPhoneNumberCodes(phoneNumberCodes);

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
        when(phoneNumberCodeRepository.save(any(PhoneNumberCode.class))).thenReturn(new PhoneNumberCode());

        String result = phoneNumberCodeService.createPhoneNumberCode(countryId, code);

        assertEquals("Successful created!", result);
        assertEquals(1, phoneNumberCodes.size());
        verify(countryRepository, times(1)).findById(countryId);
        verify(phoneNumberCodeRepository, times(1)).save(any(PhoneNumberCode.class));
        verify(countryRepository, times(1)).save(country);
    }


    @Test
    void updatePhoneNumberCode_WithValidIdAndCode_ShouldReturnSuccessfulMessage() {
        Long id = 1L;
        String code = "456";

        PhoneNumberCode phoneNumberCode = new PhoneNumberCode();
        phoneNumberCode.setId(id);

        when(phoneNumberCodeRepository.findById(id)).thenReturn(Optional.of(phoneNumberCode));
        when(phoneNumberCodeRepository.save(any(PhoneNumberCode.class))).thenReturn(new PhoneNumberCode());

        String result = phoneNumberCodeService.updatePhoneNumberCode(id, code);

        assertEquals("Successful updated!", result);
        assertEquals(code, phoneNumberCode.getCode());
        verify(phoneNumberCodeRepository, times(1)).findById(id);
        verify(phoneNumberCodeRepository, times(1)).save(phoneNumberCode);
    }

    @Test
    void getAllPhoneNumberCodes_ShouldReturnAllCodes() {
        List<PhoneNumberCode> expectedCodes = new ArrayList<>();
        expectedCodes.add(new PhoneNumberCode("123"));
        expectedCodes.add(new PhoneNumberCode("456"));
        expectedCodes.add(new PhoneNumberCode("789"));

        when(phoneNumberCodeRepository.findAll()).thenReturn(expectedCodes);

        List<PhoneNumberCode> actualCodes = phoneNumberCodeService.getAllPhoneNumberCodes();

        assertEquals(expectedCodes.size(), actualCodes.size());
        assertEquals(expectedCodes, actualCodes);
        verify(phoneNumberCodeRepository, times(1)).findAll();
    }

    @Test
    void getPhoneNumberCodeById_WithValidId_ShouldReturnCode() {
        Long id = 1L;
        PhoneNumberCode expectedCode = new PhoneNumberCode("123");

        when(phoneNumberCodeRepository.findById(id)).thenReturn(Optional.of(expectedCode));

        PhoneNumberCode actualCode = phoneNumberCodeService.getPhoneNumberCodeById(id);

        assertEquals(expectedCode, actualCode);
        verify(phoneNumberCodeRepository, times(1)).findById(id);
    }

    @Test
    void deletePhoneNumberCode_WithExistingId_ShouldReturnSuccessfulDeleted() {
        Long id = 1L;

        when(phoneNumberCodeRepository.findById(id)).thenReturn(Optional.of(new PhoneNumberCode()));
        doNothing().when(phoneNumberCodeRepository).deleteById(id);

        String result = phoneNumberCodeService.deletePhoneNumberCode(id);

        assertEquals("Successful deleted", result);
        verify(phoneNumberCodeRepository, times(1)).findById(id);
        verify(phoneNumberCodeRepository, times(1)).deleteById(id);
    }

    @Test
    void deletePhoneNumberCode_WithNonExistingId_ShouldReturnObjectNotFoundMessage() {
        Long id = 1L;

        when(phoneNumberCodeRepository.findById(id)).thenReturn(Optional.empty());

        String result = phoneNumberCodeService.deletePhoneNumberCode(id);

        assertEquals("Object with id 1 not found", result);
        verify(phoneNumberCodeRepository, times(1)).findById(id);
        verify(phoneNumberCodeRepository, never()).deleteById(anyLong());
    }


    @Test
    void addListPhoneNumberCode_WithNonExistingCountry_ShouldThrowException() {
        List<PhoneNumberCode> phoneNumberCodeList = new ArrayList<>();
        PhoneNumberCode phoneNumberCode = new PhoneNumberCode("123", new Country());
        phoneNumberCodeList.add(phoneNumberCode);

        when(countryRepository.findById(phoneNumberCode.getCountry().getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> phoneNumberCodeService.addListPhoneNumberCode(phoneNumberCodeList));

        assertEquals("Country not found", exception.getMessage());
        verify(countryRepository, times(1)).findById(phoneNumberCode.getCountry().getId());
        verify(phoneNumberCodeRepository, never()).save(any(PhoneNumberCode.class));
    }


}