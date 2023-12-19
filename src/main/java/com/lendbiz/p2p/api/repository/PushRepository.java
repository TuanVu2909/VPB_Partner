package com.lendbiz.p2p.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.NotificationsPushEntity;

@Repository
public interface PushRepository extends JpaRepository<NotificationsPushEntity, String> {
    @Procedure("NotificationsPushEntity.getOsNotifications")
    List<NotificationsPushEntity> getOsNotifications();

    @Transactional
    @Modifying
    @Query(value = "update bg_notifications set os_status = ?1 where custid = ?2 and id = ?3", nativeQuery = true)
    int updateNotifications(int status, String custid, String id);
}
