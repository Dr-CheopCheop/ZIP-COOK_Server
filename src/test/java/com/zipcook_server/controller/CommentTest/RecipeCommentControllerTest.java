package com.zipcook_server.controller.CommentTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.RecipeCommentdto;
import com.zipcook_server.data.entity.Comment.RecipeComment;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.repository.Comment.RecipeCommentRepository;
import com.zipcook_server.repository.Recipe.RecipeRepository;
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
class RecipeCommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;


    @Autowired
    private RecipeCommentRepository recipeCommentRepository;


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();
        recipeCommentRepository.deleteAll();
    }



    @Test
    @DisplayName("recipe댓글 작성")
    public void test1() throws Exception {


        RecipePost recipePost = RecipePost.builder()
                .title("test title")
                .build();
        recipeRepository.save(recipePost);

        CommentCreate commentCreate = CommentCreate.builder()
                .board_id(recipePost.getId())
                .nickname("nickname")
                .content("comment:)")
                .regDate(new Date())
                .build();


        String json = objectMapper.writeValueAsString(commentCreate);

        // when
        mockMvc.perform(post("/recipe-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment saved successfully!"))
                .andDo(print());

    }


    @Test
    @DisplayName("recipe댓글 보기")
    void test2() throws Exception {

        RecipePost recipePost = RecipePost.builder()
                .title("test title2")
                .build();
        recipeRepository.save(recipePost);

        List<RecipeComment> recipeComments = IntStream.range(0, 5)
                .mapToObj(i -> RecipeComment.builder()
                        .nickname("nickname")
                        .recipePost(recipePost)
                        .content("comment" + i)
                        .regDate(new Date())
                        .build())
                .collect(Collectors.toList());

        recipeCommentRepository.saveAll(recipeComments);

        // expected
        mockMvc.perform(get("/recipe-comment/{boardId}",recipePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("recipe댓글 수정")
    void test3() throws Exception {


        RecipePost recipePost = RecipePost.builder()
                .title("test title3")
                .build();
        recipeRepository.save(recipePost);

        RecipeComment recipeComment=RecipeComment.builder()
                .nickname("nickname")
                .recipePost(recipePost)
                .content("comment")
                .regDate(new Date())
                .build();

        recipeCommentRepository.save(recipeComment);
        RecipeComment comment=recipeCommentRepository.findByNickname("nickname").get();


        RecipeCommentdto recipeCommentdto = RecipeCommentdto.builder()
                .nickname("nickname")
                .board_id(recipePost.getId())
                .content("update comment:)")
                .regDate(new Date())
                .build();

        String json = objectMapper.writeValueAsString(recipeCommentdto);

        // expected
        mockMvc.perform(put("/recipe-comment/update/{commentId}",comment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("recipe댓글 삭제")
    void test4() throws Exception {


        RecipePost recipePost = RecipePost.builder()
                .title("test title4")
                .build();
        recipeRepository.save(recipePost);

        RecipeComment recipeComment=RecipeComment.builder()
                .nickname("nickname")
                .recipePost(recipePost)
                .content("comment")
                .regDate(new Date())
                .build();

        recipeCommentRepository.save(recipeComment);
        RecipeComment comment=recipeCommentRepository.findByNickname("nickname").get();

        //when
        mockMvc.perform(delete("/recipe-comment/delete/{commentId}", comment.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }

}