package com.lendbiz.p2p.api.repository;

import java.util.List;
import java.util.stream.Stream;

import com.lendbiz.p2p.api.entity.Card9PayEntity;
import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicRepository extends JpaRepository<Card9PayEntity_v2, String> {
    // @Procedure(name = "get_trans")
    // List<Card9PayEntity> getTransHistory();

    @Procedure("Card9PayEntity_v2.findViaProcedure")
    List<Card9PayEntity_v2> findViaProcedure(@Param("p_custId") String custId);

}
