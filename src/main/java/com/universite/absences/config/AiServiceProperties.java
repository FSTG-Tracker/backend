package com.universite.absences.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.ai-service")
public class AiServiceProperties {

    private String url = "http://localhost:8000";
    private Duration connectTimeout = Duration.ofSeconds(5);
    private Duration readTimeout = Duration.ofSeconds(30);
    private int maxRetries = 3;
    private Duration retryDelay = Duration.ofMillis(500);
}
