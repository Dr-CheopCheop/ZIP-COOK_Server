package com.zipcook_server.controller;

import com.zipcook_server.data.dto.recipe.RecipeCreate;
import com.zipcook_server.data.dto.recipe.RecipeEdit;
import com.zipcook_server.data.dto.recipe.RecipeResponse;
import com.zipcook_server.data.request.RecipeSearch;
import com.zipcook_server.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> recipePost(@RequestBody @Valid RecipeCreate recipeCreate) throws IOException {
        recipeService.write(recipeCreate);
        return ResponseEntity.ok("Post shared successfully!");
    }

    @GetMapping("/{boardId}")
    public RecipeResponse getPost(@PathVariable Long boardId) throws IOException {
        return recipeService.get(boardId);

    }

    @GetMapping()
    public List<RecipeResponse> getList(@ModelAttribute RecipeSearch recipeSearch){
        return recipeService.getList(recipeSearch);
    }

    @GetMapping("/search/{title}")
    public List<RecipeResponse> searchByTitle(@PathVariable String title) throws IOException {
        return recipeService.searchByTitle(title);
    }


    @PatchMapping("/{boardId}")
    public void edit(@PathVariable Long boardId, @RequestBody @Valid RecipeEdit request){
        recipeService.edit(boardId,request);

    }


    @DeleteMapping("/{boardId}")
    public void delete(@PathVariable Long boardId){
        recipeService.delete(boardId);

    }


}


