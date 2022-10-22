package com.lendbiz.p2p.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.lendbiz.p2p.api.entity.NotificationsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNotificationsRepository extends JpaRepository<NotificationsEntity, Integer> {
    @Procedure("NotificationsEntity.getNotifications")
    List<NotificationsEntity> getNotifications(@Param("pv_custId") String cif);

    @Transactional
    @Modifying
    @Query(value = "update bg_notifications set status = ?1 where custid = ?2 and id = ?3", nativeQuery = true)
    int updateNotifications(int status, String custid, String id);
}
