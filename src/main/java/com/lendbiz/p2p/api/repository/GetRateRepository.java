package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GetEndRateEntity;

public interface GetRateRepository extends JpaRepository<GetEndRateEntity, String> {

        @Procedure("GetEndRateEntity.getRateCal")
        GetEndRateEntity getRateCal(@Param("pv_amt") float amt, @Param("pv_payType") String pv_coinAmount,
                        @Param("pv_startDate") String pv_startDate);

}
