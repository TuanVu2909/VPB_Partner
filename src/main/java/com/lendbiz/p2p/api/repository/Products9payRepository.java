package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.Product9PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface Products9payRepository extends JpaRepository<Product9PayEntity, String> {

}
