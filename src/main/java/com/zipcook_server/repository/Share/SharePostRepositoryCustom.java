package com.zipcook_server.repository.Share;

import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.request.MainSearch;
import com.zipcook_server.data.request.ShareMainSearch;
import com.zipcook_server.data.request.ShareSearch;

import java.util.List;

public interface SharePostRepositoryCustom {
    List<SharePost> getList(ShareSearch ShareSearch);

    List<SharePost> getMainList(ShareMainSearch ShareSearch);

    List<SharePost> maingetList(MainSearch mainSearch);

}
