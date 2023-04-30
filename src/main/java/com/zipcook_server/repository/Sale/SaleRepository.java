package com.zipcook_server.repository.Sale;

import com.zipcook_server.data.entity.SalePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SalePost, Long>, SalePostRepositoryCustom {

    List<SalePost> findByTitleContaining(String title);

}
