package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.SumGrowthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SumGrowthRepository extends JpaRepository<SumGrowthEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into sum_growth values (fundPkgInfo_seq.nextval, to_date(:nDate),:sum,:pid)", nativeQuery = true)
    void save(@Param("nDate")String nDate, @Param("sum") String sum,@Param("pid") String pid);

}
