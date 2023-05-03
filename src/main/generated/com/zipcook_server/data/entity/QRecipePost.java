package com.zipcook_server.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipePost is a Querydsl query type for RecipePost
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipePost extends EntityPathBase<RecipePost> {

    private static final long serialVersionUID = -1526653539L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipePost recipePost = new QRecipePost("recipePost");

    public final ListPath<String, StringPath> content = this.<String, StringPath>createList("content", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> ingredients = this.<String, StringPath>createList("ingredients", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath level = createString("level");

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final NumberPath<Integer> serving = createNumber("serving", Integer.class);

    public final StringPath summary = createString("summary");

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final StringPath title = createString("title");

    public final QUser user;

    public QRecipePost(String variable) {
        this(RecipePost.class, forVariable(variable), INITS);
    }

    public QRecipePost(Path<? extends RecipePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipePost(PathMetadata metadata, PathInits inits) {
        this(RecipePost.class, metadata, inits);
    }

    public QRecipePost(Class<? extends RecipePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

