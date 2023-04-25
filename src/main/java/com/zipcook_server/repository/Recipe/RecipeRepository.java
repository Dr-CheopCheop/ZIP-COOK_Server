package com.zipcook_server.repository.Recipe;

import com.zipcook_server.data.entity.RecipePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipePost, Long>,RecipePostRespositoryCustom{

    List<RecipePost> findByTitleContaining(String title);

}
