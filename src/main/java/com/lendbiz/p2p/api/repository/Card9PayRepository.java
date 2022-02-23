package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.Card9PayEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Card9PayRepository extends JpaRepository<Card9PayEntity,String > {
    @Query(value = "select * from card9pay where custId = ?1 order by pay_date desc", nativeQuery = true)
    List<Card9PayEntity> findByCustId(String id);
}
