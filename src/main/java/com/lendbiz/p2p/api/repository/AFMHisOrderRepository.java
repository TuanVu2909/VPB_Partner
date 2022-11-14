package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMHisOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AFMHisOrderRepository extends JpaRepository<AFMHisOrderEntity, String> {
    AFMHisOrderEntity findByOrderid(String orderid);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO AFM_HIS_ORDER (id,custodycd,symbol,srtype,exectype,txdate,status,orderamt,orderid)" +
            " VALUES (seq_afmhisorder.nextval,:custodycd,:symbol,:srtype,:exectype,:txdate,:status,:orderamt,:orderid) ", nativeQuery = true)
    void saveBuy(@Param("custodycd")String custodycd,
                 @Param("symbol")String symbol,
                 @Param("srtype")String srtype,
                 @Param("exectype")String exectype,
                 @Param("txdate")String txdate,
                 @Param("status")String status,
                 @Param("orderamt")String orderamt,
                 @Param("orderid")String orderid
                 );

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO AFM_HIS_ORDER (id,custodycd,symbol,srtype,exectype,txdate,status,orderqtty,orderid)" +
            " VALUES (seq_afmhisorder.nextval,:custodycd,:symbol,:srtype,:exectype,:txdate,:status,:orderqtty,:orderid) ", nativeQuery = true)
    void saveSell(@Param("custodycd")String custodycd,
                  @Param("symbol")String symbol,
                  @Param("srtype")String srtype,
                  @Param("exectype")String exectype,
                  @Param("txdate")String txdate,
                  @Param("status")String status,
                  @Param("orderqtty")String orderqtty,
                  @Param("orderid")String orderid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE AFM_HIS_ORDER SET tradingdate=?1, status=?2 WHERE custodycd=?3 AND symbol=?4 AND orderid=?5", nativeQuery = true)
    int updateAfmHisOrder(String tradingdate, String status, String custodycd, String symbol, String orderid);
}
