package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class MainControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;


    @Autowired
    private ShareRepository shareRepository;


    @Autowired
    private RecipeRepository recipeRepository;


    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        saleRepository.deleteAll();
        shareRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("메인 페이지 조회")
    void testGetPage() throws Exception {


        List<RecipePost> requestPosts = IntStream.range(0, 10)
                .mapToObj(i -> RecipePost.builder()
                        .username("joy")
                        .title("title" + i)
                        .build())
                .collect(Collectors.toList());

        List<SalePost> requestPosts2 = IntStream.range(0, 10)
                .mapToObj(i -> SalePost.builder()
                        .username("joy")
                        .title("title" + i)
                        .build())
                .collect(Collectors.toList());

        List<SharePost> requestPosts3 = IntStream.range(0, 10)
                .mapToObj(i -> SharePost.builder()
                        .username("joy")
                        .title("title" + i)
                        .content("content" + i)
                        .build())
                .collect(Collectors.toList());

        recipeRepository.saveAll(requestPosts);
        saleRepository.saveAll(requestPosts2);
        shareRepository.saveAll(requestPosts3);

        // when
        // expected
        mockMvc.perform(get("/main?sharepage=1&salepage=1&recipepage=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}