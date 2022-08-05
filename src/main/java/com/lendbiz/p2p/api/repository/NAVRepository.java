package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.GmFundNAVEntity;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NAVRepository extends JpaRepository<GmFundNAVEntity , String> {
    @Query(value = "select * from gmfundnav", nativeQuery = true)
    List<GmFundNAVEntity> getAll();

    @Query(value = "select * from gmfundnav where fundid =?1", nativeQuery = true)
    List<GmFundNAVEntity> getAllByFundID(String fid);
}
