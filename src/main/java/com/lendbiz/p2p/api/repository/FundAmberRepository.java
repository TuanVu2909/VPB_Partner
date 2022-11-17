package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMBankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundAmberRepository extends JpaRepository<AFMBankInfoEntity, String> {
    AFMBankInfoEntity findByBankBin(String bank_bin);
}
