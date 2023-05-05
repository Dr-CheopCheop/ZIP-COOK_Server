package com.zipcook_server.data.dto.share;

import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sharedto {

    private Long id;

    @Valid
    private User user;

    @Size(max=30)
    private String title;

    @Size(max=80)
    private String content;

    private Date regDate;

    private String filepath;

    public Sharedto(SharePost post){
        this.user=post.getUser();
        this.id= post.getId();
        this.title= post.getTitle();
        this.content=post.getContent();
        this.regDate=post.getRegDate();
        this.filepath=post.getFilepath();
    }
}