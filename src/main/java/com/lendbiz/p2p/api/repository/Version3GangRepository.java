package com.lendbiz.p2p.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lendbiz.p2p.api.entity.Version3Gang;

@Repository("version3GangRepository")
public interface Version3GangRepository extends JpaRepository<Version3Gang, String> {

	@Query(value = "select version from bg_versions b where b.id = (select max(c.id) from bg_versions c) ", nativeQuery = true)
	Version3Gang getVersion();

}
