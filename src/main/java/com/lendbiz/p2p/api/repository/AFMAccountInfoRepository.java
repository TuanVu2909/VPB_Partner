package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMAccountInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AFMAccountInfoRepository extends JpaRepository<AFMAccountInfoEntity, String> {
    AFMAccountInfoEntity findByMobile(String mobile);
}
