package com.zipcook_server.data.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDto implements Serializable {
    private String question;

    public void setQuestion(String question) {
        this.question = question;
    }
}