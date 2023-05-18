package com.zipcook_server.controller.CommentTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.ShareCommentdto;
import com.zipcook_server.data.entity.Comment.ShareComment;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.repository.Comment.ShareCommentRepository;
import com.zipcook_server.repository.Share.ShareRepository;
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
class ShareCommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShareRepository shareRepository;


    @Autowired
    private ShareCommentRepository shareCommentRepository;


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        shareRepository.deleteAll();
        userRepository.deleteAll();
        shareCommentRepository.deleteAll();
    }



    @Test
    @DisplayName("share댓글 작성")
    public void test1() throws Exception {


        SharePost sharePost = SharePost.builder()
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        CommentCreate commentCreate = CommentCreate.builder()
                .board_id(sharePost.getId())
                .nickname("joy")
                .username("이역곡")
                .content("comment:)")
                .regDate(new Date())
                .build();


        String json = objectMapper.writeValueAsString(commentCreate);

        // when
        mockMvc.perform(post("/share-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment saved successfully!"))
                .andDo(print());

    }


    @Test
    @DisplayName("share댓글 보기")
    void test2() throws Exception {


        SharePost sharePost = SharePost.builder()
                .title("test title2")
                .build();
        shareRepository.save(sharePost);

        List<ShareComment> shareComments = IntStream.range(0, 5)
                .mapToObj(i -> ShareComment.builder()
                        .sharePost(sharePost)
                        .content("comment" + i)
                        .regDate(new Date())
                        .build())
                .collect(Collectors.toList());

        shareCommentRepository.saveAll(shareComments);

        // expected
        mockMvc.perform(get("/share-comment/{boardId}",sharePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("share댓글 수정")
    void test3() throws Exception {


        SharePost sharePost = SharePost.builder()
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        ShareComment shareComment=ShareComment.builder()
                .sharePost(sharePost)
                .nickname("joy")
                .username("이역곡")
                .content("comment")
                .regDate(new Date())
                .build();

        shareCommentRepository.save(shareComment);
        ShareComment comment=shareCommentRepository.findByNickname("joy").get();


        ShareCommentdto shareCommentdto = ShareCommentdto.builder()
                .board_id(sharePost.getId())
                .content("update comment:)")
                .regDate(new Date())
                .build();

        String json = objectMapper.writeValueAsString(shareCommentdto);

        // expected
        mockMvc.perform(put("/share-comment/update/{commentId}",comment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("share댓글 삭제")
    void test4() throws Exception {


        SharePost sharePost = SharePost.builder()
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        ShareComment shareComment=ShareComment.builder()
                .sharePost(sharePost)
                .nickname("joy")
                .username("이역곡")
                .content("comment")
                .regDate(new Date())
                .build();

        shareCommentRepository.save(shareComment);
        ShareComment comment=shareCommentRepository.findByNickname("joy").get();

        //when
        mockMvc.perform(delete("/share-comment/delete/{commentId}", comment.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }

}