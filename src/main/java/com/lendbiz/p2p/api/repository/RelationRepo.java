package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.BaoVietEntity;
import com.lendbiz.p2p.api.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.ArrayList;

public interface RelationRepo extends JpaRepository<RelationEntity,String> {
    @Procedure("RelationEntity.getRelation")
    ArrayList<RelationEntity> getRelation();
}
