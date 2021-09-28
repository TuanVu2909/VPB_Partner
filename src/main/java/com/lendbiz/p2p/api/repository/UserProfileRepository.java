package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.AuthProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***********************************************************************
 *
 *   @package：com.lendbiz.p2p.api.repository，@class-name：UserProfileRepository.java
 *
 *   @copyright       Copyright:   2021-2022
 *   @creator         Nguyen Pham Tuan Anh <br/>
 *   @create-time   Apr 13, 2021   14:44:30 PM
 *
 ***********************************************************************/
@Repository
public interface UserProfileRepository extends JpaRepository<AuthProfileEntity,String> {
    AuthProfileEntity findByTlName(String tlname);
}
