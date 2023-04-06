package com.zipcook_server.data;

import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.repository.RecipeRepository;
import com.zipcook_server.data.repository.ShareRepository;
import com.zipcook_server.data.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class EntitiyTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    RecipeRepository recipeRepository;
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
        @DisplayName("SharePost 저장")
        public void test2 () {
                SharePost post = SharePost.builder()
                        .id(1L)
                        .title("토마토")
                        .location("강서구")
                        .build();

                shareRepository.save(post);

                SharePost findpost = shareRepository.findById(post.getId()).get();
                Assertions.assertThat(findpost).isEqualTo(post);
    }


    @Test
    @DisplayName("RecipePost 저장")
    public void test3 () {
        RecipePost post = RecipePost.builder()
                .title("파스타")
                .build();

        recipeRepository.save(post);

        RecipePost findPost = recipeRepository.findById(post.getId()).orElse(null);
        Assertions.assertThat(findPost).isEqualTo(post);
    }
}
