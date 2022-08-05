package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.Card9PayEntity_v2;
import com.lendbiz.p2p.api.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGMRepository extends JpaRepository<ProductEntity, String> {
    @Procedure("ProductEntity.getproduct")
    List<ProductEntity> getproduct();
}
