package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.AdminGameRateByDayEntity;

public interface GameAdminRateByDayRepository extends JpaRepository<AdminGameRateByDayEntity, String> {

        @Procedure("AdminGameRateByDayEntity.getAdminRateByDay")
        List<AdminGameRateByDayEntity> getAdminRateByDay(@Param("pv_custid") String custId,
                        @Param("pv_date") String date);

}
