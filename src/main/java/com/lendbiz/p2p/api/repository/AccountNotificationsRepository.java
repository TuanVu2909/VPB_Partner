package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.NotificationsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNotificationsRepository extends JpaRepository<NotificationsEntity, Integer> {
    @Procedure("NotificationsEntity.getNotifications")
    List<NotificationsEntity> getNotifications(@Param("pv_custId") String cif);
}
