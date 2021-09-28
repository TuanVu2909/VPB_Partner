package com.lendbiz.p2p.api.repository;

import java.util.List;

import com.lendbiz.p2p.api.entity.AuthProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface AuthRepository extends JpaRepository<AuthProfileEntity, String> {

	@Query(value = "SELECT * FROM TLPROFILES", nativeQuery = true)
	List<AuthProfileEntity> getAll();
	
	@Query(value = "SELECT * FROM TLPROFILES WHERE TLNAME = :user AND PIN = GENENCRYPTPASSWORD(:password)", nativeQuery = true)
	AuthProfileEntity getById(@Param("user") String user, @Param("password") String password);
	
	@Query(nativeQuery = true, value = "SELECT PKG_LB_USERS.fn_check_login(:p_user, :p_password) FROM dual")
    int checkLogin(@Param("p_user") String user, @Param("p_password") String password);

	@Query(value = "SELECT * FROM TLPROFILES WHERE TLID = :tlId", nativeQuery = true)
	AuthProfileEntity getByTLId(@Param("tlId") String tlId);
}
