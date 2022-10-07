package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.lendbiz.p2p.api.entity.NinePayDepositEntity;

public interface NinePayDepositRepo extends JpaRepository<NinePayDepositEntity, String> {

    @Procedure("NinePayDepositEntity.insertApiTrans")
    NinePayDepositEntity insertApiTrans(@Param("p_amount") String amount, @Param("p_code") String code);
}
