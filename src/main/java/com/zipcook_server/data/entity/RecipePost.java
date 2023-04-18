package com.zipcook_server.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "UID")
    private User user;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "serving")
    private int serving;

    @Column(name = "level", length = 5)
    private String level;

    @Column(length = 1000)
    private String ingredients;

    @Column(name = "summary", length = 1000)
    private String summary;

    @ElementCollection
    @Column(name = "content")
    private List<String> content;


    @Column(name = "time")
    private int time;

    @Temporal(TemporalType.DATE)
    @Column(name = "reg_date")
    private Date regDate;

    public  RecipeEditor.RecipeEditorBuilder toEditor(){
        return RecipeEditor.builder()
                .title(title)
                .serving(serving)
                .level(level)
                .ingredients(ingredients)
                .summary(summary)
                .content(content)
                .time(time);
    }

    public void edit(RecipeEditor recipeEditor){
        title=recipeEditor.getTitle();
        serving=recipeEditor.getServing();
        level=recipeEditor.getLevel();
        ingredients=recipeEditor.getIngredients();
        summary=recipeEditor.getSummary();
        content=recipeEditor.getContent();
        time= recipeEditor.getTime();
    }

}

