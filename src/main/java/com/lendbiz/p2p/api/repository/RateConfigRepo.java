package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.RateConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateConfigRepo extends JpaRepository<RateConfigEntity, String> {

        @Query(value = "Select * from gmrateInfo", nativeQuery = true)
        List<RateConfigEntity> getRate();

}
