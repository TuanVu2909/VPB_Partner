package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.AccountAssetEntity;
import com.lendbiz.p2p.api.entity.InvestAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestAssetsRepository extends JpaRepository<InvestAssets, String> {
    @Query(value = "select documentno, amount, term, rate, start_date, end_date from invest_assets \n" +
            "        where cif = :pv_custId and invest_type = :pv_pid", nativeQuery = true)
    List<InvestAssets> getInvestAssets(@Param("pv_custId")String cif, @Param("pv_pid")int pid);

}
