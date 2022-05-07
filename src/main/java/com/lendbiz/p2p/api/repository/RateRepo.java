package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.RateEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface RateRepo extends JpaRepository<RateEntity, Integer> {

    @Query(value = "   \n" +
            "            select term, rate, cdcontent paytype from gmrateInfo r, allcode a where r.paytype = a.cdval and a.cdname = 'PAYTYPE' \n" +
            "                and pid = :pv_pid and term = :pv_term and minamt <= :amt and maxamt >= :amt", nativeQuery = true)
    List<RateEntity> getRate(@Param("pv_pid")String pid,@Param("pv_term") String term,@Param("amt") String amt);

    @Procedure("RateEntity.getRatePro")
    ArrayList<RateEntity> getRatePro(@Param("pv_pid")String pid, @Param("pv_term") String term, @Param("pv_amt") String amt);

    @Query(value = "   \n" +
            "            select term, rate, cdcontent paytype from gmrateInfo r, allcode a where r.paytype = a.cdval and a.cdname = 'PAYTYPE' \n" +
            "                and pid = :pv_pid  and minamt <= :amt and maxamt >= :amt", nativeQuery = true)
    List<RateEntity> getRateNoPeriod(@Param("pv_pid")String pid,@Param("amt") String amt);


}

