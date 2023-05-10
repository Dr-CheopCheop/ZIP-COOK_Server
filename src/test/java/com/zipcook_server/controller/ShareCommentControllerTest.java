package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.ShareCommentdto;
import com.zipcook_server.data.entity.Comment.ShareComment;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Comment.ShareCommentRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import com.zipcook_server.service.CommentService;
import com.zipcook_server.service.ShareService;
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
    private ShareService shareService;

    @Autowired
    private ShareCommentRepository shareCommentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("share댓글 작성")
    public void test1() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SharePost sharePost = SharePost.builder()
                .id(1L)
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        CommentCreate commentCreate = CommentCreate.builder()
                .user_id(user.getId())
                .board_id(sharePost.getId())
                .writer("nickname")
                .content("comment:)")
                .regDate(new Date())
                .build();


        String json = objectMapper.writeValueAsString(commentCreate);

        // when
        mockMvc.perform(post("/sharecomment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment saved successfully!"))
                .andDo(print());

    }


    @Test
    @DisplayName("share댓글 보기")
    void test2() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SharePost sharePost = SharePost.builder()
                .id(1L)
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        List<ShareComment> shareComments = IntStream.range(0, 5)
                .mapToObj(i -> ShareComment.builder()
                        .user(user)
                        .sharePost(sharePost)
                        .content("comment" + i)
                        .regDate(new Date())
                        .build())
                .collect(Collectors.toList());

        shareCommentRepository.saveAll(shareComments);

        // expected
        mockMvc.perform(get("/sharecomment/{boardId}",sharePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("share댓글 수정")
    void test3() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SharePost sharePost = SharePost.builder()
                .id(1L)
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        ShareComment shareComment=ShareComment.builder()
                .user(user)
                .sharePost(sharePost)
                .writer("nickname")
                .content("comment")
                .regDate(new Date())
                .build();

        shareCommentRepository.save(shareComment);
        List<ShareComment> shareComments=shareCommentRepository.findAll();


        ShareCommentdto shareCommentdto = ShareCommentdto.builder()
                .user_id(user.getId())
                .board_id(sharePost.getId())
                .content("update comment:)")
                .regDate(new Date())
                .build();

        String json = objectMapper.writeValueAsString(shareCommentdto);

        // expected
        mockMvc.perform(put("/sharecomment/update/{commentId}",shareComments.get(0).getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("share댓글 삭제")
    void test4() throws Exception {
        // given
        User user = User.builder()
                .id("joy")
                .email("example@example.com")
                .password("abc123")
                .location("seoul")
                .build();
        userRepository.save(user);

        SharePost sharePost = SharePost.builder()
                .id(1L)
                .title("test title")
                .build();
        shareRepository.save(sharePost);

        ShareComment shareComment=ShareComment.builder()
                .user(user)
                .sharePost(sharePost)
                .writer("nickname")
                .content("comment")
                .regDate(new Date())
                .build();

        shareCommentRepository.save(shareComment);

        //when
        mockMvc.perform(delete("/sharecomment/delete/{commentId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }

}