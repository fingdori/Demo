package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CommandService {
    void runCommand(String command) throws IOException;
}
