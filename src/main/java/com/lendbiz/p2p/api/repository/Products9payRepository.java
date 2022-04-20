package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.NotifyEntity;
import com.lendbiz.p2p.api.entity.Product9PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface Products9payRepository extends JpaRepository<Product9PayEntity, String> {
    @Procedure("Product9PayEntity.get_productcard9pay")
    List<Product9PayEntity> get_productcard9pay(@Param("s_id") String service_id);

}
