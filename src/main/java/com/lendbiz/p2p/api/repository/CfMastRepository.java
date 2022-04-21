package com.lendbiz.p2p.api.repository;

import java.util.List;
import java.util.Optional;

import com.lendbiz.p2p.api.entity.CfMast;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("cfMastRepository")
public interface CfMastRepository extends CrudRepository<CfMast, String> {

	Optional<CfMast> findByCustid(String custid);

	// get cfmast voi truong hop la nguoi vay von
	@Query(value = "SELECT c.* FROM CFMAST c ,ODMAST o WHERE c.mobilesms = ?1 "
			+ "and o.afacctno = c.custid and o.orstatus = 'G' and o.orderid = o.borgorderid", nativeQuery = true)
	List<CfMast> findByPhone(String phone);

	// get cfmast vs truong hop la nha dau tu
	@Query(value = "select * from cfmast where mobilesms = ?1", nativeQuery = true)
	List<CfMast> findByMobileSms(String phone);

	// get cfmast vs truong hop la nha dau tu
	@Query(value = "select fullname, dateofbirth, idcode, mobilesms from cfmast where custid = ?1", nativeQuery = true)
	CfMast getUserInfo(String custId);

	@Transactional
	@Modifying
	@Query(value = "update cfmast set status = 'A' where custid = ?1", nativeQuery = true)
	void activeAccount(String custId);
}
