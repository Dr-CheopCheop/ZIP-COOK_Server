package com.zipcook_server.data.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeEditor {

    private String title;

    private int serving;

    private String level;

    private String ingredients;

    private String summary;

    private List<String> content;

    private int time;
}
