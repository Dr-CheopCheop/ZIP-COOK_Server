package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.sale.SaleCreate;
import com.zipcook_server.data.dto.sale.Saledto;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import com.zipcook_server.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private SaleService saleService;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        saleRepository.deleteAll();
        userRepository.deleteAll();
    }



    @Test
    @DisplayName("/sale 요청시 db에 값이 저장된다")
    void test1() throws Exception {


        SaleCreate saleCreate = SaleCreate.builder()
                .nickname("nick")
                .title("title sale")
                .content("content sale")
                .price("15000")
                .discountPrice("10000")
                .place("market")
                .build();


        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "test.txt", "text/plain", "test file".getBytes(StandardCharsets.UTF_8));
        String json = objectMapper.writeValueAsString(saleCreate);
        MockMultipartFile salepost = new MockMultipartFile("salepost", "salepost", "application/json", json.getBytes(StandardCharsets.UTF_8));


        mockMvc.perform(multipart("/board-sale")
                        .file(multipartFile1)
                        .file(salepost))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        SalePost post = saleRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("title sale");
        assertThat(post.getContent()).isEqualTo("content sale");
    }




    @Test
    @DisplayName("글 1개 조회")
    void test2() throws Exception {


        SalePost salePost = SalePost.builder()
                .nickname("nick")
                .title("sale")
                .content("sale content")
                .price("15000")
                .discountPrice("10000")
                .place("market")
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


        List<SalePost> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> SalePost.builder()
                        .nickname("nick")
                        .title("title" + i)
                        .content("content" + i)
                        .build())
                .collect(Collectors.toList());

        saleRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/board-sale?page=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }



    @Test
    @DisplayName("글 수정")
    void test4() throws Exception {


        SaleCreate saleCreate = SaleCreate.builder()
                .nickname("nick")
                .title("title sale")
                .content("content sale")
                .build();

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test file".getBytes(StandardCharsets.UTF_8));
        saleService.write(saleCreate, file);

        List<SalePost> salePosts = saleRepository.findByTitleContaining("sale");

        Saledto update = Saledto.builder()
                .nickname("nick")
                .title("Test update post")
                .content("Test update content")
                .regDate(new Date())
                .build();

        String json = objectMapper.writeValueAsString(update);
        MockMultipartFile saledto = new MockMultipartFile("update", "update", "application/json", json.getBytes(StandardCharsets.UTF_8));

        // when
        mockMvc.perform(multipart("/board-sale/update/{boardId}", salePosts.get(0).getId())
                        .file(file)
                        .file(saledto))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Successfully!"))
                .andDo(print());


    }


    @Test
    @DisplayName("게시글 삭제")
    void test5() throws Exception {


        SaleCreate saleCreate = SaleCreate.builder()
                .nickname("nick")
                .title("title sale")
                .content("content sale")
                .build();

        MockMultipartFile File= new MockMultipartFile("file", "test.txt", "text/plain", "test file".getBytes(StandardCharsets.UTF_8));
        saleService.write(saleCreate,File);

        List<SalePost> salePost=saleRepository.findByTitleContaining("sale");

        //when
        mockMvc.perform(delete("/board-sale/{boardId}", salePost.get(0).getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }



    @Test
    @DisplayName("게시글 검색")
    void test6() throws Exception {



        SalePost salePost = SalePost.builder()
                .nickname("nick")
                .title("sale title")
                .content("Test sale content")
                .regDate(new Date())
                .build();

        saleRepository.save(salePost);

        SalePost salePost2 = SalePost.builder()
                .nickname("nick")
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



