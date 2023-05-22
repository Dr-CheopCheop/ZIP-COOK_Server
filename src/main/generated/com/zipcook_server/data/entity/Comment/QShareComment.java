package com.zipcook_server.data.entity.Comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareComment is a Querydsl query type for ShareComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareComment extends EntityPathBase<ShareComment> {

    private static final long serialVersionUID = -1623430050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShareComment shareComment = new QShareComment("shareComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final com.zipcook_server.data.entity.QSharePost sharePost;

    public final com.zipcook_server.data.entity.QUser user;

    public QShareComment(String variable) {
        this(ShareComment.class, forVariable(variable), INITS);
    }

    public QShareComment(Path<? extends ShareComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShareComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShareComment(PathMetadata metadata, PathInits inits) {
        this(ShareComment.class, metadata, inits);
    }

    public QShareComment(Class<? extends ShareComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sharePost = inits.isInitialized("sharePost") ? new com.zipcook_server.data.entity.QSharePost(forProperty("sharePost"), inits.get("sharePost")) : null;
        this.user = inits.isInitialized("user") ? new com.zipcook_server.data.entity.QUser(forProperty("user")) : null;
    }

}

