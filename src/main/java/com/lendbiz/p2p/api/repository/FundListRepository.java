package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FundListEntity;
import com.lendbiz.p2p.api.entity.InvestAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface FundListRepository extends JpaRepository<FundListEntity, String> {
    @Procedure("FundListEntity.getFundList")
    ArrayList<FundListEntity> getFundList();


}
