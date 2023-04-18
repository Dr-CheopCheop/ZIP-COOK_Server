package com.zipcook_server.controller;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.ShareEdit;
import com.zipcook_server.data.dto.share.ShareResponse;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value="/board-share")
public class ShareController {

    @Autowired
    ShareService shareService;

    @PostMapping()
    public ResponseEntity<String> sharePost(@RequestBody @Valid  ShareCreate shareCreate) throws IOException {
        shareService.write(shareCreate);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{boardId}")
    public SharePost getPost(@PathVariable Long boardId) throws IOException {
        return shareService.get(boardId);

    }

    @GetMapping()
    public List<ShareResponse> getList(@ModelAttribute ShareSearch shareSearch){
        return shareService.getList(shareSearch);
    }

    @PatchMapping("/{boardId}")
    public void edit(@PathVariable Long boardId, @RequestBody @Valid ShareEdit request){
        shareService.edit(boardId,request);

    }


    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId){
        shareService.delete(boardId);

    }


}


