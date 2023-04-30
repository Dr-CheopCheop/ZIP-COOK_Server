package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.SaleEdit;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@SpringBootTest
class SaleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        saleRepository.deleteAll();
        userRepository.deleteAll();
    }



    @Test
    @DisplayName("요청시 db에 값이 저장된다")
    void test1() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SaleCreate saleCreate = SaleCreate.builder()
                .user(user)
                .title("sale")
                .content("sale content")
                .build();

        String json = objectMapper.writeValueAsString(saleCreate);

        // when
        mockMvc.perform(post("/board-sale")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        SalePost post = saleRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("sale");
        assertThat(post.getContent()).isEqualTo("sale content");
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

        SalePost salePost = SalePost.builder()
                .user(user)
                .title("sale")
                .content("sale content")
                .build();

        saleRepository.save(salePost);


        //when
        mockMvc.perform(get("/board-sale/{boardId}", salePost.getId())
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

        List<SalePost> requestPosts = IntStream.range(0, 10)
                .mapToObj(i -> SalePost.builder()
                        .user(user)
                        .title("title" + i)
                        .content("content" + i)
                        .build())
                .collect(Collectors.toList());

        saleRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/board-sale?page=1&size=10")
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

        SalePost salePost = SalePost.builder()
                .user(user)
                .title("Test sale post")
                .content("Test sale content")
                .regDate(new Date())
                .build();

        saleRepository.save(salePost);

        SaleEdit saleEdit = SaleEdit.builder()
                .title("sale sale :)")
                .content("test sale content")
                .build();


        //when
        mockMvc.perform(patch("/board-sale/{boardId}", salePost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleEdit)))
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

        SalePost salePost = SalePost.builder()
                .user(user)
                .title("Test sale post")
                .content("Test content")
                .regDate(new Date())
                .build();

        saleRepository.save(salePost);


        //when
        mockMvc.perform(delete("/board-sale/{boardId}", salePost.getId())
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

        SaleCreate saleCreate = SaleCreate.builder()
                .user(user)
                .content("sale content")
                .build();

        String json = objectMapper.writeValueAsString(saleCreate);

        // expected
        mockMvc.perform(post("/board-sale")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력하세요"))
                .andDo(print());




    }

    @Test
    @DisplayName("게시글 검색")
    void test7() throws Exception {
        // given

        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SalePost salePost = SalePost.builder()
                .user(user)
                .title("sale title")
                .content("Test sale content")
                .regDate(new Date())
                .build();

        saleRepository.save(salePost);

        SalePost salePost2 = SalePost.builder()
                .user(user)
                .title("sale banana")
                .content("Test sale content")
                .regDate(new Date())
                .build();

        saleRepository.save(salePost2);

        // when
        mockMvc.perform(get("/board-sale/search/{title}" ,"sale")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


}



