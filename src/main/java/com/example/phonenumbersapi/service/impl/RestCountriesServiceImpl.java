package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.Idd;
import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.entity.RestCountriesApiResponse;
import com.example.phonenumbersapi.service.RestCountriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class RestCountriesServiceImpl implements RestCountriesService {

    @Override
    public Idd getPhoneNumberCode(String country) {
        String apiUrl = "https://restcountries.com/v3.1/currency/{countryCode}?fields=idd";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("countryCode", country);

        ResponseEntity<RestCountriesApiResponse[]> responseEntity = restTemplate.getForEntity(apiUrl, RestCountriesApiResponse[].class, uriVariables);

        if (responseEntity.getBody() != null && responseEntity.getBody().length > 0) {
            RestCountriesApiResponse[] responseBody = responseEntity.getBody();
            return responseBody[0].getIdd();
        } else {
            return null; // или бросить исключение, в зависимости от логики вашего приложения
        }
    }

}
