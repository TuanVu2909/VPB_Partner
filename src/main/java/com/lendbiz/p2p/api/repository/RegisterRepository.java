package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.RegisterEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface RegisterRepository extends JpaRepository<RegisterEntity, String> {

    @Procedure("RegisterEntity.register")
    RegisterEntity register(@Param("pv_mobile") String mobile, @Param("pv_deviceId") String deviceId);
}
