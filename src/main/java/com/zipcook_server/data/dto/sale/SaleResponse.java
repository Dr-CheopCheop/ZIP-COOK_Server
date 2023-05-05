package com.zipcook_server.data.dto.sale;

import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponse {

    private Long id;

    @Valid
    private User user;

    @Size(max=30)
    private String title;

    @Size(max=80)
    private String content;

    private Date regDate;

    public SaleResponse(SalePost post){
        this.user=post.getUser();
        this.id= post.getId();
        this.title= post.getTitle();
        this.content=post.getContent();
        this.regDate=post.getRegDate();
    }
}