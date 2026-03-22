package com.example.profiles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.profiles.config.AppConfig;

@Service
@Profile("test")
@RequiredArgsConstructor
public class TestGreetingService implements GreetingService {
    private final AppConfig appConfig;

    @Override
    public String getGreeting() {
        String apiKey = appConfig.getApiKey();
        return "Unit testing in progress...";
    }
}