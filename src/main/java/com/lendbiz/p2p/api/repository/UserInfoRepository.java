package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.UserInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

	@Query(value = "select c.custID, c.fullname, c.dateofbirth, c.idcode, c.mobilesms, c.email, c.status, b.biostate from cfmast c, bg_account b where c.custid = ?1 and b.custid = c.custid", nativeQuery = true)
	UserInfoEntity getUserInfo(String custId);

}
