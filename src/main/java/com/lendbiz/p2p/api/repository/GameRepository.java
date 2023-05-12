package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GameEntity;

public interface GameRepository extends JpaRepository<GameEntity, String> {

        @Procedure("GameEntity.updateGameConfig")
        GameEntity updateGameConfig(@Param("pv_id") int id,
                        @Param("pv_rate") double rate,
                        @Param("pv_giftid") int giftId,
                        @Param("pv_groupid") int groupId,
                        @Param("pv_status") int status);

        @Procedure("GameEntity.updateGameGroupTime")
        GameEntity updateGameGroupTime(@Param("pv_groupid") int groupId,
                        @Param("pv_fromtime") String fromTime,
                        @Param("pv_totime") String toTime,
                        @Param("pv_fromdate") String fromDate,
                        @Param("pv_todate") String toDate,
                        @Param("pv_status") int status,
                        @Param("pv_maxprize") int maxPrize);

        @Procedure("GameEntity.updateGamePrize")
        GameEntity updateGamePrize(@Param("pv_id") int id,
                        @Param("pv_name") String name,
                        @Param("pv_status") int status,
                        @Param("pv_ramount") int rAmount);

        @Procedure("GameEntity.insertGameHistory")
        GameEntity insertGameHistory(@Param("pv_custid") String custId,
                        @Param("pv_status") int status,
                        @Param("pv_giftid") int giftId);

}
