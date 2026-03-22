package com.example.profiles.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String apiKey;
    private List<String> features;
    private LoggingConfig logging;

    @Data
    public static class LoggingConfig {
        private String level;
    }
}
