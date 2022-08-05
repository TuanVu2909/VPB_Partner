package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FundInvestEntity;
import com.lendbiz.p2p.api.entity.InvestAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface FundInvestRepository extends JpaRepository<FundInvestEntity, String> {
    @Procedure("FundInvestEntity.getFundInvest")
    ArrayList<FundInvestEntity> getFundInvest(@Param("pv_custId")String cif);

}
