package com.zipcook_server.controller;

import com.zipcook_server.data.dto.ChatbotResponseDto;
import com.zipcook_server.data.dto.QuestionRequestDto;
import com.zipcook_server.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/chatbot")
    public ChatbotResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatbotService.askQuestion(requestDto);
    }
}