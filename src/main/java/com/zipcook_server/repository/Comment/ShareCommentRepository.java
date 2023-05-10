package com.zipcook_server.repository.Comment;

import com.zipcook_server.data.entity.Comment.ShareComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareCommentRepository extends JpaRepository<ShareComment, Long> {
    List<ShareComment> findBySharePostIdOrderByRegDateDesc(Long sharePostId);
}
