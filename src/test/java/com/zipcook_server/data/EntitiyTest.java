package com.zipcook_server.data;

import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
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



}
