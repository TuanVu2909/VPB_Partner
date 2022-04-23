package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FundInvestDetailEntity;
import com.lendbiz.p2p.api.entity.FundInvestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface FundInvestDetailRepository extends JpaRepository<FundInvestDetailEntity , String> {
    @Procedure("FundInvestDetailEntity.getFundInvestDetail")
    ArrayList<FundInvestDetailEntity> getFundInvestDetail(@Param("pv_custId")String cif,
                                                          @Param("pv_packageId")String pv_packageId);

}
