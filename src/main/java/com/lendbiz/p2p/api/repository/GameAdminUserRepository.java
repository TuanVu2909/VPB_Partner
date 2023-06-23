package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.AdminGameHistoryEntity;
import com.lendbiz.p2p.api.entity.AdminGameUserEntity;

public interface GameAdminUserRepository extends JpaRepository<AdminGameUserEntity, String> {

        @Procedure("AdminGameUserEntity.getAdminGameUser")
        List<AdminGameUserEntity> getAdminGameUser(@Param("pv_custid") String custId,
                        @Param("pv_gameid") int gameId);

}
