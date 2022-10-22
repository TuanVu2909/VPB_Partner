package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.CoinEntity;
import com.lendbiz.p2p.api.entity.TransferCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface TransFerCodeRepo extends JpaRepository<TransferCodeEntity, String> {
    @Procedure("TransferCodeEntity.genTransferCode")
    TransferCodeEntity genTransferCode (@Param("pv_custId")String cid);
}
