package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.BaoVietEntity;
import com.lendbiz.p2p.api.entity.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BaoVietRepo extends JpaRepository<BaoVietEntity, String> {

    @Procedure("BaoVietEntity.getInsurancePackage")
    ArrayList<BaoVietEntity> getInsurancePackage();
}
