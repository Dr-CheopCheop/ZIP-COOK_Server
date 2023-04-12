package com.zipcook_server.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String API_KEY = "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";
    public static final String MODEL = "text-davinci-003";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/completions";

}