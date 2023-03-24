package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.SavingProductsEntity;

@Repository
public interface SavingProductsRepository extends JpaRepository<SavingProductsEntity, String> {
    // @Procedure(name = "get_trans")
    // List<Card9PayEntity> getTransHistory();

    @Procedure("BgConfigEntity.findViaProcedure")
    List<SavingProductsEntity> findViaProcedure();

}
