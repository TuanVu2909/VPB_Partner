package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.vnpt.BgEkycEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BgEkycRepository extends JpaRepository<BgEkycEntity, String> {
    BgEkycEntity findByMobileSms(String mobileSms);
}
