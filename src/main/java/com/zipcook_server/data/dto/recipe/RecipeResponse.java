package com.zipcook_server.data.dto.recipe;

import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.User;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {

    private Long id;

    @Valid
    private User user;

    @Size(max=30)
    private String title;

    private int serving;

    @Size(max=5)
    private String level;

    private List<String> ingredients;

    @Size(max=40)
    private String summary;

    private List<String> content;


    private int time;


    private Date regDate;

    public RecipeResponse(RecipePost post){
        this.user=post.getUser();
        this.id= post.getId();
        this.title= post.getTitle();
        this.serving= post.getServing();
        this.level=post.getLevel();
        this.ingredients=post.getIngredients();
        this.summary=post.getSummary();
        this.content=post.getContent();
        this.time=post.getTime();
        this.regDate=post.getRegDate();
    }
}
