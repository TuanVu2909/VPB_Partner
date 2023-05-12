package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.GameConfigEntity;

public interface GameConfigRepository extends JpaRepository<GameConfigEntity, String> {

        @Procedure("GameConfigEntity.getGameConfig")
        List<GameConfigEntity> getGameConfig(@Param("pv_groupid") int groupId, @Param("pv_fromtime") String fromTime,
                        @Param("pv_totime") String toTime, @Param("pv_fromdate") String fromDate,
                        @Param("pv_todate") String toDate

        );

}
