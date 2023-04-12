package com.zipcook_server.service;

import com.zipcook_server.config.ChatbotConfig;
import com.zipcook_server.data.dto.ChatbotRequestDto;
import com.zipcook_server.data.dto.ChatbotResponseDto;
import com.zipcook_server.data.dto.QuestionRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatbotService {

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatbotRequestDto> buildHttpEntity(ChatbotRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatbotConfig.MEDIA_TYPE));
        headers.add(ChatbotConfig.AUTHORIZATION, ChatbotConfig.BEARER + ChatbotConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatbotResponseDto getResponse(HttpEntity<ChatbotRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatbotResponseDto> responseEntity = restTemplate.postForEntity(
                ChatbotConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatbotResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatbotResponseDto askQuestion(QuestionRequestDto requestDto) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatbotRequestDto(
                                ChatbotConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatbotConfig.MAX_TOKEN,
                                ChatbotConfig.TEMPERATURE,
                                ChatbotConfig.TOP_P
                        )
                )
        );
    }
}