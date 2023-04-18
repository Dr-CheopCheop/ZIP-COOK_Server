package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.share.ShareCreate;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ShareControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        shareRepository.deleteAll();
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

        ShareCreate shareCreate = ShareCreate.builder()
                .user(user)
                .title("tomato")
                .content("share tomato")
                .build();

        String json = objectMapper.writeValueAsString(shareCreate);

        // when
        mockMvc.perform(post("/board-share")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        SharePost post = shareRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("tomato");
        assertThat(post.getContent()).isEqualTo("share tomato");
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

        SharePost sharePost = SharePost.builder()
                .user(user)
                .title("tomato")
                .content("share tomato")
                .build();

        shareRepository.save(sharePost);


        //when
        mockMvc.perform(get("/board-share/{boardId}", sharePost.getId())
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

        List<SharePost> requestPosts = IntStream.range(0, 10)
                .mapToObj(i -> SharePost.builder()
                        .user(user)
                        .title("title" + i)
                        .content("content" + i)
                        .build())
                .collect(Collectors.toList());

        shareRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/board-share?page=1&size=10")
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
        mockMvc.perform(patch("/board-share/{boardId}", sharePost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shareEdit)))
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

        SharePost sharePost = SharePost.builder()
                .user(user)
                .title("Test share post")
                .content("Test content")
                .regDate(new Date())
                .build();

        shareRepository.save(sharePost);


        //when
        mockMvc.perform(delete("/board-share/{boardId}", sharePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }


    @Test
    @DisplayName("/share 요청시 제목은 필수값이다")
    void test6() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        ShareCreate shareCreate = ShareCreate.builder()
                .user(user)
                .content("share tomato")
                .build();

        String json = objectMapper.writeValueAsString(shareCreate);

        // expected
        mockMvc.perform(post("/board-share")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력하세요"))
                .andDo(print());




    }


}



