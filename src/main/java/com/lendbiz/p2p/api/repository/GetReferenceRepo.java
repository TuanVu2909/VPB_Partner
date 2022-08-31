package com.lendbiz.p2p.api.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.lendbiz.p2p.api.entity.ReferenceIdentity;

public interface GetReferenceRepo extends JpaRepository<ReferenceIdentity, String> {

    @Procedure("ReferenceIdentity.getReferenceId")
    ArrayList<ReferenceIdentity> getReferenceId(@Param("p_refCustId") String refCustId);

    @Query(nativeQuery = true, value = "SELECT pkg_api.checkRefExisted(:pv_custId) FROM dual")
    int checkRefExisted(@Param("pv_custId") String investId);
}
