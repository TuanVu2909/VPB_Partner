package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMBankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundAmberRepository extends JpaRepository<AFMBankInfoEntity, String> {
}
