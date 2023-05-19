package com.zipcook_server.repository.Share;

import com.zipcook_server.data.entity.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<SharePost, Long>,SharePostRepositoryCustom{

    List<SharePost> findByTitleContaining(String title);

}
