package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.lendbiz.p2p.api.entity.GetGameWinEntity;

public interface GetGameWinRepository extends JpaRepository<GetGameWinEntity, String> {

        @Procedure("GameHistoryEntity.getGameWin")
        List<GetGameWinEntity> getGameWin();

}
