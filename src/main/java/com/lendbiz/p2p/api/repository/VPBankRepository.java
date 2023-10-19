package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.bank.VPBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VPBankRepository extends JpaRepository<VPBankEntity,Integer> {

//    @Transactional
//    @Modifying
//    @Query(value = "insert into vpb_transactions values (vpb_trans_seq.nextval, to_date(:nDate),:sum,:pid)", nativeQuery = true)
//    void save(@Param("nDate")String nDate, @Param("sum") String sum, @Param("pid") String pid);
//    void insertVPBTrans();
}
