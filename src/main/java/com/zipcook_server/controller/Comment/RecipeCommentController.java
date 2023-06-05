package com.zipcook_server.controller.Comment;

import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.RecipeCommentdto;
import com.zipcook_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@ResponseBody
@RequestMapping(value="/recipe-comment")
public class RecipeCommentController {

    @Autowired
    CommentService commentService;

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody @Valid CommentCreate commentCreate) {
        commentService.recipecommentsave(commentCreate);
        return ResponseEntity.ok("Comment saved successfully!");
    }


    @GetMapping("/{boardId}")
    public List<RecipeCommentdto> getList(@PathVariable Long boardId){
        return commentService.recipecommentfindall(boardId);
    }


    @PostMapping("/update/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody @Valid RecipeCommentdto update) throws IOException {
        commentService.recipecommentupdate(commentId, update);
        return ResponseEntity.ok("Comment updated successfully!");
    }

    @DeleteMapping("/delete/{commentId}")
    public void delete(@PathVariable Long commentId){
        commentService.recipecommentdelete(commentId);

    }
}
