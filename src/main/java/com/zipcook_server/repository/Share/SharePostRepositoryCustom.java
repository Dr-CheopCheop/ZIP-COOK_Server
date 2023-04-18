package com.zipcook_server.repository.Share;

import com.zipcook_server.data.entity.SharePost;
import com.zipcook_server.data.request.ShareSearch;

import java.util.List;

public interface SharePostRepositoryCustom {
    List<SharePost> getList(ShareSearch ShareSearch);
}
