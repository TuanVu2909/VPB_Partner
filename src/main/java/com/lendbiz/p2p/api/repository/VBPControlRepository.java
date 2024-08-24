package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.bank.VPBControlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface VBPControlRepository extends JpaRepository<VPBControlEntity,String> {
    @Transactional
    @Procedure("VPBControlEntity.selectLogs")
    List<VPBControlEntity> selectLogs();
}
