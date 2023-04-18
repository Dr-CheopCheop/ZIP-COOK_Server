package com.zipcook_server.repository.Recipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipcook_server.data.entity.QRecipePost;
import com.zipcook_server.data.entity.RecipePost;
import com.zipcook_server.data.request.RecipeSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipePostRespositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RecipePost> getList(RecipeSearch recipeSearch) {
        return jpaQueryFactory.selectFrom(QRecipePost.recipePost)
                .limit(recipeSearch.getPage())
                .offset(recipeSearch.getOffset())
                .fetch();
    }
}



