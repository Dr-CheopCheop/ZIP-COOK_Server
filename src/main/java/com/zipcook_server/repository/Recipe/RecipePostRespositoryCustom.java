package com.zipcook_server.repository.Recipe;

import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.request.RecipeSearch;

import java.util.List;

public interface RecipePostRespositoryCustom {
    List<RecipePost> getList(RecipeSearch RecipeSearch);
}
