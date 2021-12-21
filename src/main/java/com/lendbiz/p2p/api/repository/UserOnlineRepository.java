package com.lendbiz.p2p.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.UserOnline;

@Repository
public interface UserOnlineRepository extends JpaRepository<UserOnline, String> {

	@Query(value = "SELECT *  FROM useronline u, cfmast c" + 
			" WHERE u.custid = c.custid AND u.sesstionid = ?1" + 
			" AND SYSDATE - u.last_change < 1800/(24*60*60)", nativeQuery = true)
	Optional<UserOnline> findBySession(String session);

	@Query(value = "SELECT DEVICEID FROM useronline u" + 
	" WHERE u.custid = ?1" + 
	" AND SYSDATE - u.last_change < 1800/(24*60*60)", nativeQuery = true)
	String findDeviceId(String custId);
}
