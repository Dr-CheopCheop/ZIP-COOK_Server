package com.zipcook_server.controller;

import com.zipcook_server.config.MonthFoodConfig;
import com.zipcook_server.data.dto.ChatbotResponseDto;
import com.zipcook_server.data.dto.QuestionRequestDto;
import com.zipcook_server.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;
    @Autowired
    private MonthFoodConfig monthFoodConfig;

    @PostMapping("/message")
    public ChatbotResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatbotService.askQuestion(requestDto);
    }

    @GetMapping("{month}")
    public String getMonthFood(@PathVariable String month) {
        String food = null;
        switch (month) {
            case "january":
                food = monthFoodConfig.getJanuary();
                break;
            case "february":
                food = monthFoodConfig.getFebruary();
                break;
            case "march":
                food = monthFoodConfig.getMarch();
                break;
            case "april":
                food = monthFoodConfig.getApril();
                break;
            case "may":
                food = monthFoodConfig.getMay();
                break;
            case "june":
                food = monthFoodConfig.getJune();
                break;
            case "july":
                food = monthFoodConfig.getJuly();
                break;
            case "august":
                food = monthFoodConfig.getAugust();
                break;
            case "september":
                food = monthFoodConfig.getSeptember();
                break;
            case "october":
                food = monthFoodConfig.getOctober();
                break;
            case "november":
                food = monthFoodConfig.getNovember();
                break;
            case "december":
                food = monthFoodConfig.getDecember();
                break;
            default:
                food = "Invalid month";
                break;
        }
        return food;
    }
}