package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.amber.AFMHisOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AFMHisOrderRepository extends JpaRepository<AFMHisOrderEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO AFM_HIS_ORDER (id,custodycd,symbol,srtype,exectype,txdate,tradingdate,status,orderamt,orderqtty,orderid,status_id)" +
            " VALUES (seq_afmhisorder.nextval,:custodycd,:symbol,:srtype,:exectype,:txdate,:tradingdate,:status,:orderamt,:orderqtty,:orderid,:status_id) ", nativeQuery = true)
    void saveData(
                 @Param("custodycd")String custodycd,
                 @Param("symbol")String symbol,
                 @Param("srtype")String srtype,
                 @Param("exectype")String exectype,
                 @Param("txdate")String txdate,
                 @Param("tradingdate")String tradingdate,
                 @Param("status")String status,
                 @Param("orderamt")String orderamt,
                 @Param("orderqtty")String orderqtty,
                 @Param("orderid")String orderid,
                 @Param("status_id")String status_id
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE AFM_HIS_ORDER SET status=?1 WHERE custodycd=?2 AND symbol=?3 AND orderid=?4 AND status_id=?5", nativeQuery = true)
    int updateAfmHisOrder(String status, String custodycd, String symbol, String orderid, String status_id);
}
