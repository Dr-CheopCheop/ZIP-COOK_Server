package com.zipcook_server.data.entity.Comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSaleComment is a Querydsl query type for SaleComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSaleComment extends EntityPathBase<SaleComment> {

    private static final long serialVersionUID = -304749190L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSaleComment saleComment = new QSaleComment("saleComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final com.zipcook_server.data.entity.QSalePost salePost;

    public final com.zipcook_server.data.entity.QUser user;

    public QSaleComment(String variable) {
        this(SaleComment.class, forVariable(variable), INITS);
    }

    public QSaleComment(Path<? extends SaleComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSaleComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSaleComment(PathMetadata metadata, PathInits inits) {
        this(SaleComment.class, metadata, inits);
    }

    public QSaleComment(Class<? extends SaleComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.salePost = inits.isInitialized("salePost") ? new com.zipcook_server.data.entity.QSalePost(forProperty("salePost"), inits.get("salePost")) : null;
        this.user = inits.isInitialized("user") ? new com.zipcook_server.data.entity.QUser(forProperty("user")) : null;
    }

}

