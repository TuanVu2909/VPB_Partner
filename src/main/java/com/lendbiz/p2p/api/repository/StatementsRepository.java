package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.StatementsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementsRepository extends JpaRepository<StatementsEntity, Integer> {
    @Procedure("StatementsEntity.getStatements")
    List<StatementsEntity> getStatements(@Param("pv_custId") String cif);
}
