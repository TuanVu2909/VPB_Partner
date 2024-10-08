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

	@Query(value = "SELECT *  FROM bg_account u, cfmast c" +
			" WHERE u.custid = c.custid AND u.sesstionid = ?1" +
			" AND SYSDATE - u.last_change < 1800/(24*60*60)", nativeQuery = true)
	Optional<UserOnline> findBySession(String session);

	@Query(value = "SELECT DEVICEID FROM bg_account u" +
			" WHERE u.custid = ?1" +
			" AND SYSDATE - u.last_change < 1800/(24*60*60)", nativeQuery = true)
	String findDeviceId(String custId);

	@Query(value = "SELECT * FROM bg_account u, cfmast c, allcode a_sex WHERE c.sex=" +
			"a_sex.cdval AND a_sex.cdname='SEX' " +
			"AND u.custid = c.custid" +
			" AND u.custid = trim(?1)" +
			" AND c.custid = trim(?1)" +
			" AND c.status != 'P'", nativeQuery = true)
	UserOnline getUserOnline(String username);

	@Query(value = "select count(*) from bg_account u, cfmast c where c.custid = ?1 and u.custid = c.custid and exists(select (1) from account_mapping a where a.custid = c.custid and pid = '17')", nativeQuery = true)
	int checkAccountMappingExist(String custId);

	@Transactional
	@Modifying
	@Query(value = "update bg_account set last_change = sysdate, numberoffail = numberoffail + 1 where custid = ?1", nativeQuery = true)
	void updateNumberFail(String custId);

	@Transactional
	@Modifying
	@Query(value = "update bg_account set last_change = sysdate, numberoffail = 0 where custid = ?1", nativeQuery = true)
	void resetFail(String custId);

	@Transactional
	@Modifying
	@Query(value = "update bg_account set biostate = ?1, deviceid = ?3 where custid = ?2", nativeQuery = true)
	void updateDeviceIdBioState(int state, String custId, String deviceId);

	@Transactional
	@Modifying
	@Query(value = "update bg_account set biostate = ?1 where custid = ?2", nativeQuery = true)
	int updateOnlyBioState(int state, String custId);

	@Transactional
	@Modifying
	@Query(value = "update bg_account set version = ?1 where custid = ?2", nativeQuery = true)
	void updateVersion(String version, String custId);

}
