package com.example.profiles.controller;

import com.example.profiles.config.AppConfig;
import com.example.profiles.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final AppConfig appConfig;
    private final GreetingService greetingService;

    @Value("${spring.datasource.url:No URL configured}")
    private String dbUrl;

    @GetMapping("/info")
    public Map<String, Object> getAppInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("Greeting", greetingService.getGreeting());
        response.put("DB_URL", dbUrl);
        response.put("ConfigDetails", appConfig);
        return response;
    }
}
