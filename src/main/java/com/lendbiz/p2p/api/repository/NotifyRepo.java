package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.NotifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifyRepo extends JpaRepository<NotifyEntity, String> {

    @Procedure("NotifyEntity.changeCoin")
    NotifyEntity changeCoin(@Param("pv_custId") String pv_custId, @Param("pv_coinAmount") String pv_coinAmount);


    @Procedure("NotifyEntity.updateReferenceId")
    NotifyEntity updateReferenceId(@Param("pv_custId") String pv_custId, @Param("pv_refId") String pv_refId);

    @Procedure("NotifyEntity.createBear")
    NotifyEntity createBear(@Param("pv_custId") String pv_custId
            , @Param("pv_pid") String pv_pid
            , @Param("pv_term") String pv_term
            , @Param("pv_rate") String pv_rate
            , @Param("pv_amt") String pv_amt
            , @Param("pv_contractId") String pv_contractId
            , @Param("pv_payType") String pv_payType);

    @Procedure("NotifyEntity.insert_trans9pay")
    NotifyEntity insert_trans9pay(@Param("cid") String pv_custId
            , @Param("tid") String tid
            , @Param("pid") String pid
            , @Param("pri") String pri
            , @Param("pstatus") String pstatus
            , @Param("scode") String scode
            , @Param("ccode") String ccode);


}
