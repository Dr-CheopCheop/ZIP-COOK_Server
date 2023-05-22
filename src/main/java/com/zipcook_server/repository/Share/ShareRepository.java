package com.zipcook_server.repository.Share;

import com.zipcook_server.data.entity.SalePost;
import com.zipcook_server.data.entity.SharePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<SharePost, Long>,SharePostRepositoryCustom{

    List<SharePost> findByTitleContainingAndLocation(String title,String location);

    Optional<SharePost> findByIdAndLocation(Long id, String location);

}
