package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FundListEntity;
import com.lendbiz.p2p.api.entity.InvestPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.ArrayList;

public interface InvestPackageRepository  extends JpaRepository<InvestPackageEntity, String> {
    @Procedure("InvestPackageEntity.getInvestPackage")
    ArrayList<InvestPackageEntity> getInvestPackage();
}
