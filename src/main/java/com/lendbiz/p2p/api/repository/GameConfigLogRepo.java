package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.GameConfigLogEntity;

@Repository("gameConfigLogRepo")
public interface GameConfigLogRepo extends JpaRepository<GameConfigLogEntity, String> {

}
