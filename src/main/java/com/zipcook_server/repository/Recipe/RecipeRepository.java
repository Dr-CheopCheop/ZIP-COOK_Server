package com.zipcook_server.repository.Recipe;

import com.zipcook_server.data.entity.RecipePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipePost, Long>,RecipePostRespositoryCustom{



}
