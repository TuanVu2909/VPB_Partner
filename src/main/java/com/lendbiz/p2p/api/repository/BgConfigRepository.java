package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.BgConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BgConfigRepository extends JpaRepository<BgConfigEntity, String> {
    // @Procedure(name = "get_trans")
    // List<Card9PayEntity> getTransHistory();

    @Procedure("BgConfigEntity.findViaProcedure")
    List<BgConfigEntity> findViaProcedure(@Param("pv_mobile") String mobile);

}
