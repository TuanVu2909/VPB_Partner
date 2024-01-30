package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VPBankRepository extends JpaRepository<VPBankEntity,String> {

    @Procedure("VPBankEntity.insertNotify")
    @Transactional
    VPBankEntity insertNotify (
            @Param("pv_source_num") String source_num,
            @Param("pv_amount") String amount,
            @Param("pv_transaction_date") String transaction_date,
            @Param("pv_transaction_id") String transaction_id,
            @Param("pv_remark") String remark,
            @Param("pv_signature") String signature
    );

    @Procedure("VPBankEntity.selectNoti")
    VPBankEntity selectNoti(@Param("pv_ft") String ft);
}