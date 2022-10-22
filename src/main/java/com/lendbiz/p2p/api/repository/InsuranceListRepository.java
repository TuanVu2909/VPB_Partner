package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.InsuranceList;
import com.lendbiz.p2p.api.entity.InsurancePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceListRepository extends JpaRepository<InsuranceList , String> {
    @Procedure("InsuranceList.getInsuranceList ")
    List<InsuranceList> getInsuranceList(@Param("pv_custId") String pv_custId);

}
