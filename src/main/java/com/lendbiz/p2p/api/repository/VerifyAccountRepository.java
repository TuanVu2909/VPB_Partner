package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.VerifyAccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface VerifyAccountRepository extends JpaRepository<VerifyAccountEntity, Integer> {

    @Procedure("RegisterEntity.verify")
    VerifyAccountEntity verify(@Param("pv_custid") String custId, @Param("pv_verifycode") String verifyCode);
}
