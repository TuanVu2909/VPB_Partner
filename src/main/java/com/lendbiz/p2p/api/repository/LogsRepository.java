package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendbiz.p2p.api.entity.ApiLogsEntity;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.repository，@class-name：LogsRepository.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   
 *   @create-time   Apr 9, 2021   6:21:37 PM   
 *
 ***********************************************************************/
public interface LogsRepository extends JpaRepository<ApiLogsEntity, String>{

}
