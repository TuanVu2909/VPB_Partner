package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.AdminGameHistoryEntity;
import com.lendbiz.p2p.api.entity.GameHistoryEntity;

public interface GameAdminHistoryRepository extends JpaRepository<AdminGameHistoryEntity, String> {

        @Procedure("AdminGameHistoryEntity.getAdminGameHistory")
        List<AdminGameHistoryEntity> getAdminGameHistory(@Param("pv_custid") String custId,
                        @Param("pv_gameid") int gameId);

}
