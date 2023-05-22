package com.zipcook_server.controller.CommentTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.SaleCommentdto;
import com.zipcook_server.data.entity.Comment.RecipeComment;
import com.zipcook_server.data.entity.Comment.SaleComment;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Comment.SaleCommentRepository;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class SaleCommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;


    @Autowired
    private SaleCommentRepository saleCommentRepository;


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        saleRepository.deleteAll();
        userRepository.deleteAll();
        saleCommentRepository.deleteAll();
    }



    @Test
    @DisplayName("sale댓글 작성")
    public void test1() throws Exception {


        SalePost salePost = SalePost.builder()
                .title("test title")
                .build();
        saleRepository.save(salePost);

        CommentCreate commentCreate = CommentCreate.builder()
                .board_id(salePost.getId())
                .nickname("nickname")
                .content("comment:)")
                .regDate(new Date())
                .build();


        String json = objectMapper.writeValueAsString(commentCreate);

        // when
        mockMvc.perform(post("/sale-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment saved successfully!"))
                .andDo(print());

    }


    @Test
    @DisplayName("share댓글 보기")
    void test2() throws Exception {


        SalePost salePost = SalePost.builder()
                .title("test title2")
                .build();
        saleRepository.save(salePost);

        List<SaleComment> saleComments = IntStream.range(0, 5)
                .mapToObj(i -> SaleComment.builder()
                        .salePost(salePost)
                        .content("comment" + i)
                        .regDate(new Date())
                        .build())
                .collect(Collectors.toList());

        saleCommentRepository.saveAll(saleComments);

        // expected
        mockMvc.perform(get("/sale-comment/{boardId}",salePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("sale댓글 수정")
    void test3() throws Exception {


        SalePost salePost = SalePost.builder()
                .title("test title3")
                .build();
        saleRepository.save(salePost);

        SaleComment saleComment=SaleComment.builder()
                .salePost(salePost)
                .nickname("nickname")
                .content("comment")
                .regDate(new Date())
                .build();

        saleCommentRepository.save(saleComment);
        SaleComment comment=saleCommentRepository.findByNickname("nickname").get();


        SaleCommentdto saleCommentdto = SaleCommentdto.builder()
                .nickname("nickname")
                .board_id(salePost.getId())
                .content("update comment:)")
                .regDate(new Date())
                .build();

        String json = objectMapper.writeValueAsString(saleCommentdto);

        // expected
        mockMvc.perform(put("/sale-comment/update/{commentId}",comment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("sale댓글 삭제")
    void test4() throws Exception {


        SalePost salePost = SalePost.builder()
                .title("test title4")
                .build();
        saleRepository.save(salePost);

        SaleComment saleComment=SaleComment.builder()
                .salePost(salePost)
                .nickname("nickname")
                .content("comment")
                .regDate(new Date())
                .build();

        saleCommentRepository.save(saleComment);
        SaleComment comment=saleCommentRepository.findByNickname("nickname").get();

        //when
        mockMvc.perform(delete("/sale-comment/delete/{commentId}", comment.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }

}