package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.CfMast;
import com.lendbiz.p2p.api.entity.Insurance_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Insurance_infoRepository extends JpaRepository<Insurance_info , String> {
    @Transactional
    @Modifying
    @Query(value = "insert into insurance_info values(?1,?2,?3)", nativeQuery = true)
    void create(String cif,String gyc, String status);


}
