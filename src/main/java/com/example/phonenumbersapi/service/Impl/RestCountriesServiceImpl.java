package com.example.phonenumbersapi.service.Impl;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.entity.RestCountriesApiResponse;
import com.example.phonenumbersapi.service.RestCountriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class RestCountriesServiceImpl implements RestCountriesService {

    @Override
    public PhoneNumberCode getPhoneNumberCode(String country) {
        String apiUrl = "https://restcountries.com/v3.1/currency/" + country + "?fields=idd";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<RestCountriesApiResponse[]> responseEntity = restTemplate.getForEntity(apiUrl, RestCountriesApiResponse[].class);
        return Objects.requireNonNull(responseEntity.getBody())[0].getIdd();

        //вывод был(phoneNumberCode);
        //вывод был (apiUrl);
    }

}
