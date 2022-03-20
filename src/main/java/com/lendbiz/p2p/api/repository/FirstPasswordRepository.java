package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FirstPasswordEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstPasswordRepository extends JpaRepository<FirstPasswordEntity, String> {
    @Procedure("FirstPasswordEntity.firstPassword")
    FirstPasswordEntity firstPassword(@Param("pv_CustId") String custId, @Param("pv_Password") String password);
}
