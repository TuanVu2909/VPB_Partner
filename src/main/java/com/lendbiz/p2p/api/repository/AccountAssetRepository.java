package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.AccountAssetEntity;
import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAssetRepository extends JpaRepository<AccountAssetEntity, String> {
    @Procedure("AccountAssetEntity.getAccountAsset")
   AccountAssetEntity getAccountAsset(@Param("pv_custId")String cif);
}
