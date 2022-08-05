package com.lendbiz.p2p.api.repository;

import java.util.ArrayList;

import com.lendbiz.p2p.api.entity.AccountInvest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInvestRepository extends JpaRepository<AccountInvest , Integer> {
    @Procedure("AccountInvest.getAccountInvest")
    ArrayList<AccountInvest> getAccountInvest(@Param("pv_custId")String cif);
}
