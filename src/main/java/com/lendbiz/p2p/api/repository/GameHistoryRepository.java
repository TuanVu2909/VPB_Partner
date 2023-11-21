package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GameHistoryEntity;

public interface GameHistoryRepository extends JpaRepository<GameHistoryEntity, Integer> {

        @Procedure("GameHistoryEntity.getGameHistory")
        List<GameHistoryEntity> getGameHistory(@Param("pv_custid") String custId,
                        @Param("pv_gameid") int gameId);

        @Procedure("GameHistoryEntity.getAdminGameHistory")
        List<GameHistoryEntity> getAdminGameHistory(@Param("pv_custid") String custId,
                        @Param("pv_gameid") int gameId);

}
