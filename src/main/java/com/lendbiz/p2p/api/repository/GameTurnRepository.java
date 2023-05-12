package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GameTurnEntity;

public interface GameTurnRepository extends JpaRepository<GameTurnEntity, String> {

        @Procedure("GameTurnEntity.getGameTurn")
        GameTurnEntity getGameTurn(@Param("pv_custid") String custId,
                        @Param("pv_gameid") int gameId);

}
