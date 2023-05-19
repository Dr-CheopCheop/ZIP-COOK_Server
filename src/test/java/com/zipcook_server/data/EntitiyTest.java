package com.zipcook_server.data;

import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import com.zipcook_server.service.ShareService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

@AutoConfigureMockMvc
@SpringBootTest
public class EntitiyTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    ShareService shareService;

    @Autowired
    RecipeRepository recipeRepository;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("user저장")
    public void test1() {
        User user = User.builder()
                .id("joy1212")
                .email("joy@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        User finduser = userRepository.findById(user.getId()).get();
        Assertions.assertThat(finduser).isEqualTo(user);
    }

    @Test
    @Transactional
    @DisplayName("SharePost 저장")
    public void test2() {
        // given
        User user = User.builder()
                .id("johndoe")
                .email("johndoe@example.com")
                .password("password")
                .location("Test location")
                .build();
        userRepository.save(user);

        SharePost sharePost = SharePost.builder()
                .user(user)
                .title("Test share post")
                .content("Test content")
                .regDate(new Date())
                .build();

        // when
        shareRepository.save(sharePost);

        // then
        SharePost findPost = shareRepository.findById(sharePost.getId()).orElse(null);
        Assertions.assertThat(findPost).isEqualTo(sharePost);
    }


    // given


    @Test
    @Transactional
    @DisplayName("RecipePost 저장")
    public void testSaveRecipePost() {
        // given
        User user = User.builder()
                .id("johndoe")
                .email("johndoe@example.com")
                .password("password")
                .location("Test location")
                .build();
        userRepository.save(user);



        RecipePost recipePost = RecipePost.builder()
                .user(user)
                .title("Test Recipe")
                .serving(2)
                .level("easy")
                .summary("This is a test recipe")
                .ingredients(Arrays.asList("tomato","onion"))
                .content(Arrays.asList("Step 1. Do this", "Step 2. Do that", "Step 3. Do something else"))
                .time(30)
                .regDate(new Date())
                .build();

        // when
        recipeRepository.save(recipePost);

        // then
        RecipePost post = recipeRepository.findById(recipePost.getId()).orElse(null);
        Assertions.assertThat(post).isEqualTo(recipePost);

    }
}
