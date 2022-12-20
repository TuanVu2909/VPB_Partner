package com.lendbiz.p2p.api.repository;

import java.util.List;
import java.util.Optional;

import com.lendbiz.p2p.api.entity.CfMast;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
	@Query(value = "select * from cfmast where (mobilesms = ?1 or custid = ?1) and custtype = 'I' order by custid desc", nativeQuery = true)
	List<CfMast> findByMobileSms(String phone);

	// get cfmast vs truong hop la nha dau tu
	@Query(value = "select fullname, dateofbirth, idcode, mobilesms from cfmast where custid = ?1", nativeQuery = true)
	CfMast getUserInfo(String custId);

	// get cfmast vs truong hop la nha dau tu
	@Query(value = "select * from cfmast where idcode = ?1 and status = 'A' and mobilesms != ?2", nativeQuery = true)
	List<CfMast> findByIdCode(String idCode, String mobile);

	// get cfmast de ky hop dong 3gang
	@Query(value = "select c.* from cfmast c, bg_account b, contracts_info i where c.custid = b.username and i.custid = b.custid and i.contract_type = '3GANG' and c.status = 'A' and i.status = 20 order by c.custid asc", nativeQuery = true)
	List<CfMast> findAllActive();

	@Transactional
	@Modifying
	@Query(value = "update cfmast set status = 'A' where custid = ?1", nativeQuery = true)
	void activeAccount(String custId);

	@Query(value = "SELECT a.publicsherid FROM cfmast c, accesstrade a WHERE c.custid = a.custid and c.mobilesms = a.id and c.custid = ?1", nativeQuery = true)
	String getAccessTradeTrackingId(String custId);

	@Query(value = "SELECT count(*) FROM cfmast c, acaesstrade a WHERE c.custid = a.custid and c.mobilesms = a.id and c.custid = ?1", nativeQuery = true)
	int countAccessTradeAccount(String custId);
}
