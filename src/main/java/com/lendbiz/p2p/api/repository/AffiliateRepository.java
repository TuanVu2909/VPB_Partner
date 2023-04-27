package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.affiliate.GMAffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AffiliateRepository extends JpaRepository<GMAffiliateEntity, String> {

    @Query(value = "SELECT * FROM gmaffiliate g WHERE g.source IN ('accesstrade', 'hyperlead', 'mosaic') AND g.status = ?1", nativeQuery = true)
    List<GMAffiliateEntity> getAllByStatusAndSource (int status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE gmaffiliate g SET g.status = ?1 WHERE g.custid = ?2", nativeQuery = true)
    int updateByStatus(int status, String custId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE gmaffiliate g SET g.isekyc = ?1 WHERE g.custid = ?2", nativeQuery = true)
    int updateByIsEkyc(int isekyc, String custId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE gmaffiliate g SET g.issaving = ?1 WHERE g.custid = ?2", nativeQuery = true)
    int updateByIsSaving(int issaving, String custId);
}