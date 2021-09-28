package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.PartnerAccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.repository，@class-name：UserAuthRepository.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:55:30 AM   
 *
 ***********************************************************************/
@Repository
public interface UserAuthRepository extends JpaRepository<PartnerAccountEntity, String> {

	@Query(value = "SELECT * FROM API_PARTNER_ACCOUNT", nativeQuery = true)
	List<PartnerAccountEntity> getAll();
}
