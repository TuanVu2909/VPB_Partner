package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.TermEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TermRepo extends JpaRepository<TermEntity , String> {
    @Procedure("TermEntity.getTerm")
    List<TermEntity> getTerm(@Param("pv_pid")String pid);

}
