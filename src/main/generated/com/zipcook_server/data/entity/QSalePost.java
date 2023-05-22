package com.zipcook_server.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalePost is a Querydsl query type for SalePost
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalePost extends EntityPathBase<SalePost> {

    private static final long serialVersionUID = 1183894422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalePost salePost = new QSalePost("salePost");

    public final StringPath content = createString("content");

    public final StringPath discountPrice = createString("discountPrice");

    public final StringPath filepath = createString("filepath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath nickname = createString("nickname");

    public final StringPath place = createString("place");

    public final StringPath price = createString("price");

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final ListPath<com.zipcook_server.data.entity.Comment.SaleComment, com.zipcook_server.data.entity.Comment.QSaleComment> saleComments = this.<com.zipcook_server.data.entity.Comment.SaleComment, com.zipcook_server.data.entity.Comment.QSaleComment>createList("saleComments", com.zipcook_server.data.entity.Comment.SaleComment.class, com.zipcook_server.data.entity.Comment.QSaleComment.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final QUser user;

    public QSalePost(String variable) {
        this(SalePost.class, forVariable(variable), INITS);
    }

    public QSalePost(Path<? extends SalePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalePost(PathMetadata metadata, PathInits inits) {
        this(SalePost.class, metadata, inits);
    }

    public QSalePost(Class<? extends SalePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

