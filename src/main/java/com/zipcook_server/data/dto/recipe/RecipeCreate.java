package com.zipcook_server.data.dto.recipe;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCreate {
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


    private String time;


    private Date regDate;

    private String nickname;
}
