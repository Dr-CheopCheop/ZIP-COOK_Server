package com.zipcook_server.repository.Share;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipcook_server.data.entity.QSalePost;
import com.zipcook_server.data.entity.QSharePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.data.request.ShareSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ShareRepositoryImpl implements SharePostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SharePost> getList(ShareSearch shareSearch) {
        return jpaQueryFactory.selectFrom(QSharePost.sharePost)
                .where(QSharePost.sharePost.location.eq(shareSearch.getLocation()))
                .orderBy(QSharePost.sharePost.id.desc())
                .limit(10)
                .offset(shareSearch.getOffset())
                .fetch();
    }

    @Override
    public List<SharePost> maingetList(MainSearch mainSearch) {
        return jpaQueryFactory.selectFrom(QSharePost.sharePost)
                .orderBy(QSharePost.sharePost.id.desc())
                .limit(5)
                .offset(mainSearch.sharegetOffset())
                .fetch();
    }
}
