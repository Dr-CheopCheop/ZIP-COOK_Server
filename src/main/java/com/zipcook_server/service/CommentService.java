package com.zipcook_server.service;

import com.zipcook_server.data.dto.comment.CommentCreate;
import com.zipcook_server.data.dto.comment.RecipeCommentdto;
import com.zipcook_server.data.dto.comment.SaleCommentdto;
import com.zipcook_server.data.dto.comment.ShareCommentdto;
import com.zipcook_server.data.entity.Comment.RecipeComment;
import com.zipcook_server.data.entity.Comment.SaleComment;
import com.zipcook_server.data.entity.Comment.ShareComment;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.exception.PostNotFound;
import com.zipcook_server.repository.Comment.RecipeCommentRepository;
import com.zipcook_server.repository.Comment.SaleCommentRepository;
import com.zipcook_server.repository.Comment.ShareCommentRepository;
import com.zipcook_server.repository.Recipe.RecipeRepository;
import com.zipcook_server.repository.Sale.SaleRepository;
import com.zipcook_server.repository.Share.ShareRepository;
import com.zipcook_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    ShareCommentRepository shareCommentRepository;

    @Autowired
    SaleCommentRepository saleCommentRepository;
    @Autowired
    RecipeCommentRepository recipeCommentRepository;
    @Autowired
    UserRepository userRepository;

    ///////share댓글/////////////////////////////
    @Transactional
    public void sharecommentsave(CommentCreate commentCreate){

        if (commentCreate == null) {
            throw new IllegalArgumentException("CommentCreate cannot be null.");
        }

        SharePost post=shareRepository.findById(commentCreate.getBoard_id())
                .orElseThrow(PostNotFound::new);


        ShareComment shareComment = ShareComment.builder()
                .nickname(commentCreate.getNickname())
                .username(commentCreate.getUsername())
                .content(commentCreate.getContent())
                .sharePost(post)
                .regDate(new Date())
                .build();

        shareCommentRepository.save(shareComment);
    }

    public List<ShareCommentdto> sharecommentfindall(Long sharepost_id){
        return shareCommentRepository.findBySharePostIdOrderByIdDesc(sharepost_id).stream()
                .map(ShareCommentdto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void sharecommentupdate(Long id, ShareCommentdto update) throws IOException {
        ShareComment shareComment=shareCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        shareComment.toUpdateEntity(update);
        shareCommentRepository.save(shareComment);
    }

    public void sharecommentdelete(Long id){
        ShareComment shareComment =shareCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        shareCommentRepository.delete(shareComment);
    }





    ///////sale댓글/////////////////////////////
    @Transactional
    public void salecommentsave(CommentCreate commentCreate){

        if (commentCreate == null) {
            throw new IllegalArgumentException("CommentCreate cannot be null.");
        }

        SalePost post=saleRepository.findById(commentCreate.getBoard_id())
                .orElseThrow(PostNotFound::new);



        SaleComment saleComment = SaleComment.builder()
                .nickname(commentCreate.getNickname())
                .username(commentCreate.getUsername())
                .content(commentCreate.getContent())
                .salePost(post)
                .regDate(new Date())
                .build();

        saleCommentRepository.save(saleComment);
    }

    public List<SaleCommentdto> salecommentfindall(Long salepost_id){
        return saleCommentRepository.findBySalePostIdOrderByIdDesc(salepost_id).stream()
                .map(SaleCommentdto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void salecommentupdate(Long id, SaleCommentdto update) throws IOException {
        SaleComment saleComment=saleCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        saleComment.toUpdateEntity(update);
        saleCommentRepository.save(saleComment);
    }

    public void salecommentdelete(Long id){
        SaleComment saleComment =saleCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        saleCommentRepository.delete(saleComment);
    }




    ///////recipe댓글/////////////////////////////
    @Transactional
    public void recipecommentsave(CommentCreate commentCreate){

        if (commentCreate == null) {
            throw new IllegalArgumentException("CommentCreate cannot be null.");
        }

        RecipePost post=recipeRepository.findById(commentCreate.getBoard_id())
                .orElseThrow(PostNotFound::new);


        RecipeComment recipeComment = RecipeComment.builder()
                .nickname(commentCreate.getNickname())
                .username(commentCreate.getUsername())
                .content(commentCreate.getContent())
                .recipePost(post)
                .regDate(new Date())
                .build();

        recipeCommentRepository.save(recipeComment);
    }

    public List<RecipeCommentdto> recipecommentfindall(Long recipepost_id){
        return recipeCommentRepository.findByRecipePostIdOrderByIdDesc(recipepost_id).stream()
                .map(RecipeCommentdto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void recipecommentupdate(Long id, RecipeCommentdto update) throws IOException {
        RecipeComment recipeComment=recipeCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        recipeComment.toUpdateEntity(update);
        recipeCommentRepository.save(recipeComment);
    }

    public void recipecommentdelete(Long id){
        RecipeComment recipeComment =recipeCommentRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        recipeCommentRepository.delete(recipeComment);
    }
}
