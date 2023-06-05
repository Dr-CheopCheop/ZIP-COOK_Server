package com.zipcook_server.controller;

import com.zipcook_server.data.dto.share.ShareCreate;
import com.zipcook_server.data.dto.share.Sharedto;
import com.zipcook_server.data.request.ShareMainSearch;
import com.zipcook_server.data.request.ShareSearch;
import com.zipcook_server.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;

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

    @GetMapping("/{location}/{boardId}")
    public Sharedto getPost(@PathVariable String location,@PathVariable Long boardId) throws IOException {
        return shareService.get(boardId,location);

    }

    @GetMapping()
    public List<Sharedto> getList(@ModelAttribute ShareSearch shareSearch){
        return shareService.getList(shareSearch);
    }


    @GetMapping("/main")
    public List<Sharedto> getMainList(@ModelAttribute ShareMainSearch shareMainSearch){
        return shareService.getMainList(shareMainSearch);
    }

    @GetMapping("/{location}/search/{title}")
    public List<Sharedto> searchByTitle(@PathVariable String location,@PathVariable String title) throws IOException {
        return shareService.searchByTitle(title,location);
    }

    @GetMapping("/{location}/update/{boardId}")
    public void updateForm(@PathVariable String location,@PathVariable Long boardId, Model model)  {
        Sharedto sharedto =shareService.get(boardId,location);
        model.addAttribute("shareboardupdate", sharedto);
    }

    @PostMapping("/{location}/update/{boardId}")
    public ResponseEntity<String> update(@PathVariable String location,@PathVariable Long boardId, @RequestPart("sharepost") @Valid Sharedto update,
                                         @RequestPart(value="file",required = true) MultipartFile file) throws IOException {
        shareService.update(boardId,location,update,file);
        return ResponseEntity.ok("Updated Successfully!");
    }

    @DeleteMapping("/{location}/{boardId}")
    public void delete(@PathVariable String location,@PathVariable Long boardId){
        shareService.delete(boardId,location);

    }


}


