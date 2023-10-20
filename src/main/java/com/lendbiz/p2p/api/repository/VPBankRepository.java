package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;

@Repository
public interface VPBankRepository extends JpaRepository<VPBankEntity,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into vpb_transactions values (vpb_trans_seq.nextval, ?1,?2,?3,?4,?5,to_date(?6, 'yyyyMMdd'),to_date(?7, 'yyyyMMdd hh24:mi:ss'),?8,?9,null,null,?10)", nativeQuery = true)
    void insertVPBTrans(
            String master_acc_num,
            String virtual_acc_num,
            String virtual_name,
            String virtual_key,
            String amount,
            String booking_date,
            String transaction_date,
            String transaction_id,
            String remark,
            String signature
    );
}
