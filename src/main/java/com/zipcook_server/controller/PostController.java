package com.zipcook_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @RequestMapping("/post")
    public String get(){
        return "hello world";
    }
}
