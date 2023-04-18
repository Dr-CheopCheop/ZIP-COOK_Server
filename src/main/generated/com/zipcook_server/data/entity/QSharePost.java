package com.zipcook_server.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharePost is a Querydsl query type for SharePost
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSharePost extends EntityPathBase<SharePost> {

    private static final long serialVersionUID = 873798896L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharePost sharePost = new QSharePost("sharePost");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final StringPath title = createString("title");

    public final QUser user;

    public QSharePost(String variable) {
        this(SharePost.class, forVariable(variable), INITS);
    }

    public QSharePost(Path<? extends SharePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharePost(PathMetadata metadata, PathInits inits) {
        this(SharePost.class, metadata, inits);
    }

    public QSharePost(Class<? extends SharePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

