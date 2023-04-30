package com.zipcook_server.data.dto.sale;

import lombok.*;

import javax.validation.constraints.Size;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class SaleEdit {


    @Size(max=30)
    private String title;


    @Size(max=80)
    private String content;

    @Builder
    public SaleEdit(String title, String content){
        this.title=title;
        this.content=content;
    }

}
