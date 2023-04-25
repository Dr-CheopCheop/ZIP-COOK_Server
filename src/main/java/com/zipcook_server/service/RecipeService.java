package com.zipcook_server.service;

import com.zipcook_server.data.dto.recipe.RecipeCreate;
import com.zipcook_server.data.dto.recipe.RecipeEdit;
import com.zipcook_server.data.dto.recipe.RecipeResponse;
import com.zipcook_server.data.entity.RecipeEditor;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.RecipeSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    public void write(RecipeCreate recipeCreate) {
        User user = userRepository.findById(recipeCreate.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        RecipePost recipePost = RecipePost.builder()
                .user(user)
                .title(recipeCreate.getTitle())
                .serving(recipeCreate.getServing())
                .level(recipeCreate.getLevel())
                .ingredients(recipeCreate.getIngredients())
                .summary(recipeCreate.getSummary())
                .content(recipeCreate.getContent())
                .time(recipeCreate.getTime())
                .regDate(new Date())
                .build();

        recipeRepository.save(recipePost);
    }

    public RecipeResponse get(Long id){
        RecipePost post=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return RecipeResponse.builder()
                .id(post.getId())
                .user(post.getUser())
                .title(post.getTitle())
                .serving(post.getServing())
                .level(post.getLevel())
                .ingredients(post.getIngredients())
                .summary(post.getSummary())
                .content(post.getContent())
                .time(post.getTime())
                .regDate(new Date())
                .build();

    }


    public List<RecipeResponse> getList(RecipeSearch recipeSearch){
        return recipeRepository.getList(recipeSearch).stream()
                .map(RecipeResponse::new)
                .collect(Collectors.toList());
    }


    public List<RecipeResponse> searchByTitle(String title) {
        return recipeRepository.findByTitleContaining(title).stream()
                .map(RecipeResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, RecipeEdit recipeEdit){
        RecipePost recipePost=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        RecipeEditor.RecipeEditorBuilder recipeEditorBuilder=recipePost.toEditor();

        RecipeEditor recipeEditor=recipeEditorBuilder
                .title(recipeEdit.getTitle())
                .serving(recipeEdit.getServing())
                .level(recipeEdit.getLevel())
                .ingredients(recipeEdit.getIngredients())
                .summary(recipeEdit.getSummary())
                .content(recipeEdit.getContent())
                .time(recipeEdit.getTime())
                .build();

        recipePost.edit(recipeEditor);
    }

    public void delete(Long id){
        RecipePost post=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        recipeRepository.delete(post);
    }

}
