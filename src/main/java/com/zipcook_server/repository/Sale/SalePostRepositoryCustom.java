package com.zipcook_server.repository.Sale;

import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.data.request.SaleMainSearch;
import com.zipcook_server.data.request.SaleSearch;

import java.util.List;

public interface SalePostRepositoryCustom {

    List<SalePost> getList(SaleSearch SaleSearch);

    List<SalePost> getMainList();

    List<SalePost> maingetList(MainSearch mainSearch);
}
