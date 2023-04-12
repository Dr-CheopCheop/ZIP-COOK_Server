package com.zipcook_server.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
// OpenAI API에서 반환하는 JSON 데이터의 choices 배열의 각 원소를
// 객체로 매핑하여 사용하기 위한 클래스
public class Choice implements Serializable {

    private String text; // 생성된 답변
    private Integer index; //생성된 답변이 몇 번재로 생성된 것인지
    @JsonProperty("finish_reason")
    private String finishReason; // 생성된 답변의 생성이 완료되었는지

    @Builder
    public Choice(String text, Integer index, String finishReason) {
        this.text = text;
        this.index = index;
        this.finishReason = finishReason;
    }
}
