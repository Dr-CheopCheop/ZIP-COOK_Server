package com.zipcook_server.repository.Comment;

import com.zipcook_server.data.entity.Comment.RecipeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeCommentRepository extends JpaRepository<RecipeComment, Long> {
    List<RecipeComment> findByRecipePostIdOrderByIdDesc(Long recipePostId);

    Optional<RecipeComment> findByWriter(String writer);
}
