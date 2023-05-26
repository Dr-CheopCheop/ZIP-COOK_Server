package com.zipcook_server.controller;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.Sharedto;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@ResponseBody
@RequestMapping(value="/board-share")
public class ShareController {

    @Autowired
    ShareService shareService;


    @PostMapping()
    public ResponseEntity<String> sharePost(@RequestPart("sharepost") @Valid  ShareCreate shareCreate,
                                            @RequestPart(value="file",required = true) MultipartFile file) throws IOException {

        shareService.write(shareCreate,file);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{boardId}")
    public Sharedto getPost(@PathVariable Long boardId) throws IOException {
        return shareService.get(boardId);

    }

    @GetMapping()
    public List<Sharedto> getList(@ModelAttribute ShareSearch shareSearch){
        return shareService.getList(shareSearch);
    }

    @GetMapping("/search/{title}")
    public List<Sharedto> searchByTitle(@PathVariable String title) throws IOException {
        return shareService.searchByTitle(title);
    }

    @GetMapping("/update/{boardId}")
    public void updateForm(@PathVariable Long boardId, Model model)  {
        Sharedto sharedto =shareService.get(boardId);
        model.addAttribute("shareboardupdate", sharedto);
    }

    @PostMapping("/update/{boardId}")
    public ResponseEntity<String> update(@PathVariable Long boardId, @RequestPart @Valid Sharedto update,
                                             @RequestPart(value="file",required = true) MultipartFile file) throws IOException {
        shareService.update(boardId,update,file);
        return ResponseEntity.ok("Updated Successfully!");
    }

    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId){
        shareService.delete(boardId);

    }


}


