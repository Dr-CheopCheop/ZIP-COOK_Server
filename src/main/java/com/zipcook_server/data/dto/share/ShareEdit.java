package com.zipcook_server.data.dto.share;

import lombok.*;

import javax.validation.constraints.Size;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class ShareEdit {


    @Size(max=30, message = "Title must be less than or equal to 30 characters.")
    private String title;


    @Size(max=80, message = "Content must be less than or equal to 80 characters.")
    private String content;

    @Builder
    public ShareEdit(String title,String content){
        this.title=title;
        this.content=content;
    }

}
