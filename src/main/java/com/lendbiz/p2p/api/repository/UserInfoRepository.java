package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.UserInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

	@Query(value = "select custID, fullname, dateofbirth, idcode, mobilesms, status  from cfmast where custid = ?1", nativeQuery = true)
	UserInfoEntity getUserInfo(String custId);

}
