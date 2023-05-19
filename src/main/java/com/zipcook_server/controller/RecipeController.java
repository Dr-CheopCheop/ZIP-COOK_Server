package com.zipcook_server.controller;

import com.zipcook_server.data.dto.recipe.RecipeCreate;
import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.request.RecipeSearch;
import com.zipcook_server.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value="/board-recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping()
    public ResponseEntity<String> sharePost(@RequestPart("recipepost") @Valid RecipeCreate recipeCreate,
                                            @RequestPart(value="file",required = true) MultipartFile file) throws IOException {

        recipeService.write(recipeCreate,file);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{boardId}")
    public Recipedto getPost(@PathVariable Long boardId) throws IOException {
        return recipeService.get(boardId);

    }

    @GetMapping()
    public List<Recipedto> getList(@ModelAttribute RecipeSearch recipeSearch){
        return recipeService.getList(recipeSearch);
    }

    @GetMapping("/search/{title}")
    public List<Recipedto> searchByTitle(@PathVariable String title) throws IOException {
        return recipeService.searchByTitle(title);
    }


    @GetMapping("/update/{boardId}")
    public void updateForm(@PathVariable Long boardId, Model model)  {
        Recipedto recipedto =recipeService.get(boardId);
        model.addAttribute("recipeboardupdate", recipedto);
    }

    @PostMapping("/update/{boardId}")
    public ResponseEntity<String> update(@PathVariable Long boardId, @RequestPart @Valid Recipedto update,
                                             @RequestPart(value="file",required = true) MultipartFile file) throws IOException {
        recipeService.update(boardId,update,file);
        return ResponseEntity.ok("Updated Successfully!");
    }


    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId){
        recipeService.delete(boardId);

    }


}


