package com.zipcook_server.repository.Comment;

import com.zipcook_server.data.entity.Comment.SaleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleCommentRepository extends JpaRepository<SaleComment, Long> {
    List<SaleComment> findBySalePostIdOrderByIdDesc(Long salePostId);

    Optional<SaleComment> findByNickname(String nickname);
}
