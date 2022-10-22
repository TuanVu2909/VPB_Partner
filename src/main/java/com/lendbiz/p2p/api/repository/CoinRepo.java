package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CoinRepo extends JpaRepository<CoinEntity , String> {

    @Procedure("CoinEntity.getCoin")
    CoinEntity getCoin (@Param("pv_custId")String cid);
}
