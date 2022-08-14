package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lendbiz.p2p.api.entity.CurrentDateEntity;

public interface CurrentDateRepo extends JpaRepository<CurrentDateEntity, Integer> {

        @Query(value = "SELECT to_char(getcurrdate, 'yyyyMMdd') currentdate from dual", nativeQuery = true)
        CurrentDateEntity getCurrentDate();

}
