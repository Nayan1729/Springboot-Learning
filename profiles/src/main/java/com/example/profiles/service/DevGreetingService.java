package com.example.profiles.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hello from the DEV environment!";
    }
}
