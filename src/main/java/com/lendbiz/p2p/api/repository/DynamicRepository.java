package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicRepository extends JpaRepository<Card9PayEntity_v2, String> {
    // @Procedure(name = "get_trans")
    // List<Card9PayEntity> getTransHistory();
//
    @Procedure("Card9PayEntity_v2.findViaProcedure")
    List<Card9PayEntity_v2> findViaProcedure(@Param("p_custId") String custId,@Param("p_sdate") String p_sdate,@Param("p_edate") String p_edate);

//    @Procedure("ABC.FINDTRANSBYDATE")
//    List<Card9PayEntity_v3> findViaProcedure(@Param("pdate") String date
//    ,@Param("pedate") String edate,@Param("pcif") String pcif);

}
