package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.PkgFundInfoEntity;
import com.lendbiz.p2p.api.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PkgFundInfoRepository extends JpaRepository<PkgFundInfoEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into fundPkgInfo values (fundPkgInfo_seq.nextval, to_date(:nDate),:growth,:f_code,:pid)", nativeQuery = true)
    void save(@Param("nDate")String nDate, @Param("growth") String growth, @Param("f_code") String f_code,@Param("pid") String pid);

}
