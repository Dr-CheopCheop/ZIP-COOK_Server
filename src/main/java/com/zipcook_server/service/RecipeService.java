package com.zipcook_server.service;

import com.zipcook_server.data.dto.recipe.RecipeCreate;
import com.zipcook_server.data.dto.recipe.Recipedto;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.User;
import com.zipcook_server.data.request.RecipeSearch;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    public void write(RecipeCreate recipeCreate, MultipartFile file) throws IOException {


        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        RecipePost recipePost = RecipePost.builder()
                .nickname(recipeCreate.getNickname())
                .title(recipeCreate.getTitle())
                .serving(recipeCreate.getServing())
                .level(recipeCreate.getLevel())
                .ingredients(recipeCreate.getIngredients())
                .summary(recipeCreate.getSummary())
                .content(recipeCreate.getContent())
                .time(recipeCreate.getTime())
                .regDate(new Date())
                .filepath(savepath)
                .build();

        recipeRepository.save(recipePost);
    }

    public Recipedto get(Long id){
        RecipePost post=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return Recipedto.builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .title(post.getTitle())
                .serving(post.getServing())
                .level(post.getLevel())
                .ingredients(post.getIngredients())
                .summary(post.getSummary())
                .content(post.getContent())
                .time(post.getTime())
                .regDate(new Date())
                .filepath(post.getFilepath())
                .build();

    }



    public List<Recipedto> getList(RecipeSearch recipeSearch){
        return recipeRepository.getList(recipeSearch).stream()
                .map(Recipedto::new)
                .collect(Collectors.toList());
    }

    public List<Recipedto> getAll(){
        return recipeRepository.findAll().stream()
                .map(Recipedto::new)
                .collect(Collectors.toList());
    }

    public List<Recipedto> searchByTitle(String title) {
        return recipeRepository.findByTitleContaining(title).stream()
                .map(Recipedto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, Recipedto update, MultipartFile file) throws IOException {
        RecipePost recipePost=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(recipePost.getFilepath());
        deleteFile.delete();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        String savepath="/Users/seunghee/Documents/boardimages/"+fileName;
        File saveFile = new File(savepath);
        file.transferTo(saveFile);

        recipePost.toUpdateEntity(update,savepath);
        recipeRepository.save(recipePost);
    }

    public void delete(Long id){
        RecipePost post=recipeRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        File deleteFile = new File(post.getFilepath());
        deleteFile.delete();

        recipeRepository.delete(post);
    }

}
