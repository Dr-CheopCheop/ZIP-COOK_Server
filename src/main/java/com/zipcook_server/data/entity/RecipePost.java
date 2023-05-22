package com.zipcook_server.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.entity.Comment.RecipeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ElementCollection
    @Column(name = "ingredients")
    private List<String> ingredients;

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

    private String nickname;

    @OneToMany(mappedBy = "recipePost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    @Builder.Default
    private List<RecipeComment> recipeComments = new ArrayList<>();

    private String filepath;

    public void toUpdateEntity(Recipedto recipeUpdate, String filepath) {
        this.title = recipeUpdate.getTitle();
        this.serving=recipeUpdate.getServing();
        this.level= recipeUpdate.getLevel();
        this.ingredients=recipeUpdate.getIngredients();
        this.summary= recipeUpdate.getSummary();
        this.content = recipeUpdate.getContent();
        this.time= recipeUpdate.getTime();
        this.regDate = new Date();
        this.filepath = filepath;
    }

}

