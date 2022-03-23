package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.BankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankInfoEntity,String> {

}
