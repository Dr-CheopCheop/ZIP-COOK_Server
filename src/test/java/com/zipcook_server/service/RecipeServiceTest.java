package com.zipcook_server.service;

import com.zipcook_server.data.dto.recipe.RecipeEdit;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class RecipeServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    @DisplayName("글 제목 수정")
    @Transactional
    void test1() {
        User user = User.builder()
                .id("johndoe")
                .email("johndoe@example.com")
                .password("password")
                .location("Test location")
                .build();
        userRepository.save(user);



        List<String> content = new ArrayList<>();
        content.add("Step 1. Do this");
        content.add("Step 2. Do that");
        content.add("Step 3. Do something else");

        List<String> ingredients = new ArrayList<>();
        ingredients.add("tomato");
        ingredients.add("onion");

        RecipePost recipePost = RecipePost.builder()
                .user(user)
                .title("Test recipe")
                .serving(2)
                .level("easy")
                .ingredients(ingredients)
                .summary("This is a test recipe")
                .content(content)
                .time(30)
                .regDate(new Date())
                .build();

        recipeRepository.save(recipePost);

        RecipeEdit recipeEdit = RecipeEdit.builder()
                .title("title edit test")
                .serving(2)
                .level("easy")
                .ingredients(ingredients)
                .summary("This is a test recipe")
                .content(content)
                .time(30)
                .build();

        //when
        recipeService.edit(recipePost.getId(), recipeEdit);

        //then
        RecipePost changedPost = recipeRepository.findById(recipePost.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다 id=" + recipePost.getId()));
        assertEquals("title edit test", changedPost.getTitle());


    }


    @Test
    @DisplayName("게시물 삭제")
    @Transactional
    void test2() {

        //given
        User user = User.builder()
                .id("johndoe")
                .email("johndoe@example.com")
                .password("password")
                .location("Test location")
                .build();
        userRepository.save(user);

        List<String> content = new ArrayList<>();
        content.add("Step 1. Do this");
        content.add("Step 2. Do that");
        content.add("Step 3. Do something else");

        List<String> ingredients = new ArrayList<>();
        content.add("tomato");
        content.add("onion");

        RecipePost recipePost= RecipePost.builder()
                .user(user)
                .title("Test recipe")
                .serving(2)
                .level("easy")
                .ingredients(ingredients)
                .summary("This is a test recipe")
                .content(content)
                .time(30)
                .regDate(new Date())
                .build();

        recipeRepository.save(recipePost);

        //when
        recipeService.delete(recipePost.getId());
        assertEquals(0,recipeRepository.count());

    }


}








