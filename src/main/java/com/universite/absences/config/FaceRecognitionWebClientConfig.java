package com.universite.absences.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class FaceRecognitionWebClientConfig {

    @Bean
    public WebClient faceRecognitionWebClient(AiServiceProperties properties) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        (int) properties.getConnectTimeout().toMillis())
                .responseTimeout(properties.getReadTimeout());

        return WebClient.builder()
                .baseUrl(properties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
