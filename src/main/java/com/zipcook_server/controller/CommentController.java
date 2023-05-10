package com.zipcook_server.controller;

import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/sharecomment/save")
    public ResponseEntity<String> sharecommentsave(@RequestBody CommentCreate commentCreate) {
        commentService.sharecommentsave(commentCreate);
        return ResponseEntity.ok("Comment saved successfully!");
    }
}
