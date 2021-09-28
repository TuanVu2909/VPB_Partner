package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.SearchEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/***********************************************************************  
 *  
 *   @package：com.lendbiz.p2p.api.repository，@class-name：AuthRepository.java   
 *     
 *   @copyright       Copyright:   2021-2022     
 *   @creator         Hoang Thanh Tu <br/>   
 *   @create-time   Apr 9, 2021   10:55:26 AM   
 *
 ***********************************************************************/
@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, String> {

	@Query(value = "SELECT * FROM SEARCH", nativeQuery = true)
	List<SearchEntity> getAll();

}
