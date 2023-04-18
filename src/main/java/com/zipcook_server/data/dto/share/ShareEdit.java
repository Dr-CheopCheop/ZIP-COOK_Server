package com.zipcook_server.data.dto.share;

import lombok.*;

import javax.validation.constraints.Size;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class ShareEdit {


    @Size(max=30)
    private String title;


    @Size(max=80)
    private String content;

    @Builder
    public ShareEdit(String title,String content){
        this.title=title;
        this.content=content;
    }

}
