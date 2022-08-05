package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.VerifyEmailEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface VerifyEmailRepository extends JpaRepository<VerifyEmailEntity, Integer> {

    @Procedure("VerifyEmailEntity.verify")
    VerifyEmailEntity verify(@Param("p_custid") String custId, @Param("p_email") String email, @Param("p_otp") String otp, @Param("p_state") int state);
}
