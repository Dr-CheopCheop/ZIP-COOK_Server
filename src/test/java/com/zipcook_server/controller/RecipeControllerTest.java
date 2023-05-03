package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.recipe.RecipeCreate;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class RecipeControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("/share 요청시 db에 값이 저장된다")
    void test1() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        List<String> content = new ArrayList<>();
        content.add("Step 1. Do this");
        content.add("Step 2. Do that");
        content.add("Step 3. Do something else");

        List<String> ingredients = new ArrayList<>();
        ingredients.add("tomato");
        ingredients.add("onion");

        RecipeCreate recipeCreate = RecipeCreate.builder()
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

        String json = objectMapper.writeValueAsString(recipeCreate);

        // when
        mockMvc.perform(post("/board-recipe")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        RecipePost post = recipeRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("Test recipe");
        assertThat(post.getUser().getId()).isEqualTo("joy");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() throws Exception {
        //given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
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


        //when
        mockMvc.perform(get("/board-recipe/{boardId}", recipePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("글 여러개 조회")
    void test3() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        List<RecipePost> requestPosts = IntStream.range(0, 10)
                .mapToObj(i -> RecipePost.builder()
                        .user(user)
                        .title("title" + i)
                        .build())
                .collect(Collectors.toList());


        recipeRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/board-recipe?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("글 수정")
    void test4() throws Exception {
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
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
        mockMvc.perform(patch("/board-recipe/{boardId}", recipePost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeEdit)))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("게시글 삭제")
    void test5() throws Exception {
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
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


        //when
        mockMvc.perform(delete("/board-recipe/{boardId}", recipePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("게시글 검색")
    void test6() throws Exception {
        // given

        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        List<String> content = new ArrayList<>();
        content.add("Step 1. Do this");
        content.add("Step 2. Do that");
        content.add("Step 3. Do something else");

        List<String> ingredients = new ArrayList<>();
        ingredients.add("tomato");
        ingredients.add("onion");


        RecipePost recipe = RecipePost.builder()
                .user(user)
                .title("Spaghetti with Meatballs")
                .serving(4)
                .level("easy")
                .ingredients(ingredients)
                .summary("Classic spaghetti and meatballs")
                .content(content)
                .time(60)
                .build();

        recipeRepository.save(recipe);

        // when
        mockMvc.perform(get("/board-recipe/search/{title}" ,recipe.getTitle())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}