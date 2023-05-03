package com.zipcook_server.data.dto.recipe;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RecipeEdit {

    @Size(max=30)
    private String title;


    private int serving;

    @Size(max=5)
    private String level;

    private  List<String> ingredients;


    private String summary;

    private List<String> content;

    private int time;

    @Builder
    public RecipeEdit(String title, int serving, String level,
                      List<String> ingredients, String summary, List<String> content, int time) {
        this.title = title;
        this.serving = serving;
        this.level = level;
        this.ingredients = ingredients;
        this.summary = summary;
        this.content = content;
        this.time = time;
    }
}
