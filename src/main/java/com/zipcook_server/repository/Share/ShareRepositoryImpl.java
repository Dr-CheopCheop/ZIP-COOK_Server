package com.zipcook_server.repository.Share;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipcook_server.data.entity.QSharePost;
import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.request.ShareSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShareRepositoryImpl implements SharePostRespositoryCustom  {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SharePost> getList(ShareSearch shareSearch){
        return jpaQueryFactory.selectFrom(QSharePost.sharePost)
                .limit(shareSearch.getPage())
                .offset(shareSearch.getOffset())
                .fetch();
    }
}



