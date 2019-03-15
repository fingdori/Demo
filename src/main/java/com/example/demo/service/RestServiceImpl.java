package com.example.demo.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class RestServiceImpl implements RestService {
    @Override
    public String restPostContentJson(String address, String data) {
        URI uri = URI.create(address);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(data, headers);
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            return responseEntity.getBody();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
