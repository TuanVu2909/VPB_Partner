package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.KeysManageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeysManageRepository extends JpaRepository<KeysManageEntity, String> {
    @Query(value = "select * from keys_manage km where km.partner = 'VPBANK' and km.status='1'", nativeQuery = true)
    List<KeysManageEntity> getAllKeyVPBank();
}
