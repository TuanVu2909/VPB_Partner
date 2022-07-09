package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.User3GEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface User3GRepository extends JpaRepository<User3GEntity, Long> {
    Optional<User3GEntity> getUserByUserName(String username);
    Boolean existsAllByUserName(String username);
    @Transactional
    @Modifying
    @Query(value = " insert into users (id , username, password, enabled, role) values(users_seq.nextval, :username,:password,1,:role) ", nativeQuery = true)
    void saveUser(@Param("username")String username, @Param("password")String password, @Param("role")String role);


}