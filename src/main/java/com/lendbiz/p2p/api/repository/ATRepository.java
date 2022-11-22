package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.ATEntity;

public interface ATRepository extends JpaRepository<ATEntity, String> {

        @Procedure("ATEntity.postBack")
        List<ATEntity> postBack();

        @Procedure("ATEntity.accept")
        List<ATEntity> accept();

        @Procedure("ATEntity.reject")
        List<ATEntity> reject();

        @Procedure("ATEntity.status")
        List<ATEntity> status(@Param("pv_custid") String pv_custid, @Param("pv_status") int pv_status);

}
