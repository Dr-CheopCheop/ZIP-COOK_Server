package com.zipcook_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Comment.ShareCommentRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import com.zipcook_server.service.CommentService;
import com.zipcook_server.service.ShareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc

@SpringBootTest
class CommentControllerTest {

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
    public void shareCommentSaveTest() throws Exception {
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
        mockMvc.perform(post("/sharecomment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment saved successfully!"))
                .andDo(print());

    }

}