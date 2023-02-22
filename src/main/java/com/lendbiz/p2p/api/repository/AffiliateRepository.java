package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.affiliate.GMAffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffiliateRepository extends JpaRepository<GMAffiliateEntity, String> {
    List<GMAffiliateEntity> findAllByStatus (int status);
}