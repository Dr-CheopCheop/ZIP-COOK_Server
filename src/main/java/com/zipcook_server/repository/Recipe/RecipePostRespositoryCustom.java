package com.zipcook_server.repository.Recipe;

import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.data.request.RecipeMainSearch;
import com.zipcook_server.data.request.RecipeSearch;

import java.util.List;

public interface RecipePostRespositoryCustom {
    List<RecipePost> getList(RecipeSearch RecipeSearch);

    List<RecipePost> getMainList();

    List<RecipePost> maingetList(MainSearch mainSearch);
}
