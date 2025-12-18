package com.starian.backend.infrastructure.client;

import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class FeignClientAdapter {

    protected final Feign.Builder feignBuilder;

    protected FeignClientAdapter() {
        this.feignBuilder = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(5000, 5000));
    }

    protected <T> T createClient(String baseUrl, Class<T> clientClass) {
        return feignBuilder.target(clientClass, baseUrl);
    }

    protected Map<String, String> createHeaders(String authorization) {
        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Content-Type", "application/json");
        if (authorization != null) {
            headers.put("Authorization", authorization);
        }
        return headers;
    }
}