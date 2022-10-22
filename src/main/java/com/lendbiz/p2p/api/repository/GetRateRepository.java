package com.lendbiz.p2p.api.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GetEndRateEntity;

public interface GetRateRepository extends JpaRepository<GetEndRateEntity, String> {

        @Procedure("GetEndRateEntity.getRateCal")
        GetEndRateEntity getRateCal(@Param("pv_amt") float amt, @Param("pv_payType") String pv_coinAmount,
                        @Param("pv_startDate") String pv_startDate);

        @Query(nativeQuery = true, value = "SELECT pck_gm.fn_calIntNoTerm(:pv_amt, :pv_payType, :pv_startDate) FROM dual")
        float callIntNoTerm(@Param("pv_amt") float investId, @Param("pv_payType") String payType,
                        @Param("pv_startDate") Date date);

        @Query(nativeQuery = true, value = "SELECT pck_gm.fn_calInt(:v_investId) FROM dual")
        float callInt(@Param("v_investId") int investId);
}
