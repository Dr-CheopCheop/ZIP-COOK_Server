package com.zipcook_server.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1306999462L;

    public static final QUser user = new QUser("user");

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath location = createString("location");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.zipcook_server.data.entity.Comment.RecipeComment, com.zipcook_server.data.entity.Comment.QRecipeComment> recipeComments = this.<com.zipcook_server.data.entity.Comment.RecipeComment, com.zipcook_server.data.entity.Comment.QRecipeComment>createList("recipeComments", com.zipcook_server.data.entity.Comment.RecipeComment.class, com.zipcook_server.data.entity.Comment.QRecipeComment.class, PathInits.DIRECT2);

    public final ListPath<RecipePost, QRecipePost> recipeEntities = this.<RecipePost, QRecipePost>createList("recipeEntities", RecipePost.class, QRecipePost.class, PathInits.DIRECT2);

    public final ListPath<com.zipcook_server.data.entity.Comment.SaleComment, com.zipcook_server.data.entity.Comment.QSaleComment> saleComments = this.<com.zipcook_server.data.entity.Comment.SaleComment, com.zipcook_server.data.entity.Comment.QSaleComment>createList("saleComments", com.zipcook_server.data.entity.Comment.SaleComment.class, com.zipcook_server.data.entity.Comment.QSaleComment.class, PathInits.DIRECT2);

    public final ListPath<SalePost, QSalePost> saleEntities = this.<SalePost, QSalePost>createList("saleEntities", SalePost.class, QSalePost.class, PathInits.DIRECT2);

    public final ListPath<com.zipcook_server.data.entity.Comment.ShareComment, com.zipcook_server.data.entity.Comment.QShareComment> shareComments = this.<com.zipcook_server.data.entity.Comment.ShareComment, com.zipcook_server.data.entity.Comment.QShareComment>createList("shareComments", com.zipcook_server.data.entity.Comment.ShareComment.class, com.zipcook_server.data.entity.Comment.QShareComment.class, PathInits.DIRECT2);

    public final ListPath<SharePost, QSharePost> shareEntities = this.<SharePost, QSharePost>createList("shareEntities", SharePost.class, QSharePost.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

