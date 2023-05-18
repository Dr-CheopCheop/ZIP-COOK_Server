package com.zipcook_server.repository.Comment;

import com.zipcook_server.data.entity.Comment.ShareComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareCommentRepository extends JpaRepository<ShareComment, Long> {
    List<ShareComment> findBySharePostIdOrderByIdDesc(Long sharePostId);

    Optional<ShareComment> findByNickname(String nickname);
}
