package com.example.phonenumbersapi.service.impl;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import com.example.phonenumbersapi.entity.RestCountriesApiResponse;
import com.example.phonenumbersapi.service.RestCountriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class RestCountriesServiceImpl implements RestCountriesService {

    @Override
    public PhoneNumberCode getPhoneNumberCode(String country) {
        String apiUrl = "https://restcountries.com/v3.1/currency/" + country + "?fields=idd";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<RestCountriesApiResponse[]> responseEntity = restTemplate.getForEntity(apiUrl, RestCountriesApiResponse[].class);

        RestCountriesApiResponse[] responseBody = responseEntity.getBody();
        if (responseBody != null && responseBody.length > 0) {
            return responseBody[0].getIdd();
        } else {
            // Обработка случая, когда ответ от API пустой или null
            return null; // или бросить исключение, в зависимости от логики вашего приложения
        }


    }

}
