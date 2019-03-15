package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface RestService {
    String restPostContentJson(String address, String data);
}
