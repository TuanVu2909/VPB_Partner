package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.Card9PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;

@Repository
public interface Card9PayRepository extends JpaRepository<Card9PayEntity,String > {

}
