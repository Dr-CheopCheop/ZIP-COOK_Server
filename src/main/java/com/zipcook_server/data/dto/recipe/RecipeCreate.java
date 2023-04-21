package com.zipcook_server.data.dto.recipe;

import com.zipcook_server.data.entity.User;
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
    private User user;

    @Size(max=30)
    private String title;

    private int serving;

    @Size(max=5)
    private String level;

    @Size(max=150)
    private String ingredients;

    @Size(max=40)
    private String summary;

    private List<String> content;


    private int time;


    private Date regDate;
}