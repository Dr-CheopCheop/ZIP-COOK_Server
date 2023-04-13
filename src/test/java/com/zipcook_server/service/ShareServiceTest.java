package com.zipcook_server.service;

import com.zipcook_server.data.dto.share.ShareEdit;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
class ShareServiceTest {
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
        shareRepository.deleteAll();
    }

    @Test
    @DisplayName("글 제목 수정")
    void test1() {
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

        shareRepository.save(sharePost);

        ShareEdit shareEdit = ShareEdit.builder()
                .title("apple")
                .content("Test content")
                .build();

        //when
        shareService.edit(sharePost.getId(), shareEdit);

        //then
        SharePost changedPost = shareRepository.findById(sharePost.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다 id=" + sharePost.getId()));
        assertEquals("apple", changedPost.getTitle());


    }


    @Test
    @DisplayName("게시물 삭제")
    void test2() {

        //given
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

        shareRepository.save(sharePost);

        //when
        shareService.delete(sharePost.getId());
        assertEquals(0,shareRepository.count());

    }
}