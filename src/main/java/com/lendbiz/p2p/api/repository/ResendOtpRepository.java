package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.ResendOtpEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResendOtpRepository extends JpaRepository<ResendOtpEntity, String> {

    @Procedure("ResendOtpEntity.resendOtp")
    ResendOtpEntity resendOtp(@Param("p_mobile") String mobile);

}
