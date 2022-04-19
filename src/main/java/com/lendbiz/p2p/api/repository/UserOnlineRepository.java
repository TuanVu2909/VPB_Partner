package com.lendbiz.p2p.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	@Query(value = "SELECT * FROM useronline u, cfmast c, allcode a_sex WHERE c.sex=" +
			"a_sex.cdval AND a_sex.cdname='SEX' " +
			"AND u.custid = c.custid" +
			" AND (TRIM(u.username) = TRIM(?1) or " +
			"	  c.phone = trim(?1) or c.mobile = trim(?1) or " +
			"c.mobilesms = trim(?1))", nativeQuery = true)
	UserOnline getUserOnline(String username);

	@Query(value = "select count(*) from useronline u, cfmast c where c.custid = ?1 and u.custid = c.custid and exists(select (1) from account_mapping a where a.custid = c.custid and pid = '17')", nativeQuery = true)
	int checkAccountMappingExist(String custId);

}
