package com.lendbiz.p2p.api.repository;


import com.lendbiz.p2p.api.entity.InvestPackageDetailEntity;
import com.lendbiz.p2p.api.entity.InvestPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface InvestPackageDetailRepository  extends JpaRepository<InvestPackageDetailEntity, String> {
    @Procedure("InvestPackageDetailEntity.getInvestPackageDetail")
    List<InvestPackageDetailEntity> getInvestPackageDetail(@Param("pv_packageId") String pkId);
}
