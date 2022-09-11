package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.WithdrawEntity;



public interface WithdrawRepo extends JpaRepository<WithdrawEntity, String> {

        @Procedure("WithdrawEntity.subtractBalance")
        WithdrawEntity subtractBalance(@Param("pv_custId") String custId,
                        @Param("pv_amt") double amount, @Param("pv_type") String type);

}
