package com.zipcook_server.data.dto.recipe;

import com.zipcook_server.data.entity.RecipePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipedto {

    private Long id;

    @Valid
    private String uid;

    @Size(max=30)
    private String title;

    private String serving;

    @Size(max=5)
    private String level;

    private List<String> ingredients;

    @Size(max=40)
    private String summary;

    private List<String> content;


    private int time;


    private Date regDate;

    private String filepath;

    private String nickname;

    public Recipedto(RecipePost post){
        this.nickname= post.getNickname();
        this.id= post.getId();
        this.title= post.getTitle();
        this.serving= post.getServing();
        this.level=post.getLevel();
        this.ingredients=post.getIngredients();
        this.summary=post.getSummary();
        this.content=post.getContent();
        this.time=post.getTime();
        this.regDate=post.getRegDate();
        this.filepath=post.getFilepath();
    }
}
