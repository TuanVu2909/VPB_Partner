package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.AccountAssetEntity;
import com.lendbiz.p2p.api.entity.AccountInvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountInvestRepository extends JpaRepository<AccountInvest , Integer> {
    @Procedure("AccountInvest.getAccountInvest")
    ArrayList<AccountInvest> getAccountInvest(@Param("pv_custId")String cif);
}
