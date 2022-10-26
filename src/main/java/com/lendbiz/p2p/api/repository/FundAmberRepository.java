package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMBankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundAmberRepository extends JpaRepository<AFMBankInfoEntity, String> {
    @Query(nativeQuery = true, value = "SELECT abi.* FROM AFM_BANK_INFO abi WHERE abi.BANK_BIN != '0'")
    List<AFMBankInfoEntity> listBankInfo();
}
