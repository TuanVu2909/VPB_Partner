package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    @Query(value = "select id from tracking_otp where otp   = ?1", nativeQuery = true)
    String findIDByOtp(String otp);
}
