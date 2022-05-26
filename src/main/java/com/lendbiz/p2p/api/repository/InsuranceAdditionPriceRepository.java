package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.InsuranceAdditionPrice;
import com.lendbiz.p2p.api.entity.InsurancePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceAdditionPriceRepository extends JpaRepository<InsuranceAdditionPrice, String> {
    @Procedure("InsuranceAdditionPrice.getInsuranceAdditionPrice")
    List<InsuranceAdditionPrice> getInsuranceAdditionPrice(@Param("pv_pkgId") String pv_pkgId, @Param("pv_age") String pv_age);
}
