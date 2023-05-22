package com.zipcook_server.repository.Sale;

import com.zipcook_server.data.entity.SalePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<SalePost, Long>, SalePostRepositoryCustom {

    List<SalePost> findByTitleContainingAndLocation(String title,String location);

    Optional<SalePost> findByIdAndLocation(Long id, String location);

}
