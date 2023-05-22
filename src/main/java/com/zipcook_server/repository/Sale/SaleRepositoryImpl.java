package com.zipcook_server.repository.Sale;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipcook_server.data.entity.QSalePost;
import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.data.request.SaleSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SaleRepositoryImpl implements SalePostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SalePost> getList(SaleSearch saleSearch) {
        return jpaQueryFactory.selectFrom(QSalePost.salePost)
                .where(QSalePost.salePost.location.eq(saleSearch.getLocation()))
                .orderBy(QSalePost.salePost.id.desc())
                .limit(10)
                .offset(saleSearch.getOffset())
                .fetch();
    }

    @Override
    public List<SalePost> maingetList(MainSearch mainSearch) {
        return jpaQueryFactory.selectFrom(QSalePost.salePost)
                .orderBy(QSalePost.salePost.id.desc())
                .limit(5)
                .offset(mainSearch.salegetOffset())
                .fetch();
    }
}
