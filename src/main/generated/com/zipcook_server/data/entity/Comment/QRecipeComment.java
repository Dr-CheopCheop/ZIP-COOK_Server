package com.zipcook_server.data.entity.Comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeComment is a Querydsl query type for RecipeComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipeComment extends EntityPathBase<RecipeComment> {

    private static final long serialVersionUID = -324972077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeComment recipeComment = new QRecipeComment("recipeComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.zipcook_server.data.entity.QRecipePost recipePost;

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final com.zipcook_server.data.entity.QUser user;

    public final StringPath writer = createString("writer");

    public QRecipeComment(String variable) {
        this(RecipeComment.class, forVariable(variable), INITS);
    }

    public QRecipeComment(Path<? extends RecipeComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeComment(PathMetadata metadata, PathInits inits) {
        this(RecipeComment.class, metadata, inits);
    }

    public QRecipeComment(Class<? extends RecipeComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipePost = inits.isInitialized("recipePost") ? new com.zipcook_server.data.entity.QRecipePost(forProperty("recipePost"), inits.get("recipePost")) : null;
        this.user = inits.isInitialized("user") ? new com.zipcook_server.data.entity.QUser(forProperty("user")) : null;
    }

}

