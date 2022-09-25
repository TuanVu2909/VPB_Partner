package com.lendbiz.p2p.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lendbiz.p2p.api.model.ContractInfo;

@Repository
public interface ContractInfoRepository extends CrudRepository<ContractInfo, String> {

    @Query(value = "SELECT * FROM CONTRACTS_INFO WHERE CONTRACT_TYPE = '3GANG'", nativeQuery = true)
    Iterable<ContractInfo> getAll();

    @Query(value = "SELECT * FROM CONTRACTS_INFO WHERE SIGNED_DATE > SYSDATE - 1 AND PATH IS NOT NULL AND STATUS = 4 ORDER BY ID ASC", nativeQuery = true)
    List<ContractInfo> getAllNotProcessing();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO CONTRACTS_INFO ( ID,CUSTID,URL,PATH,CONTRACT_TYPE,CONTRACT_NAME,CONTRACT_ID, GENERATE_DATE, STATUS) VALUES (:id,:custId,:url,:path,:contractType,:contractName,:contractId, sysdate, 20)", nativeQuery = true)
    void create(@Param("id") String id, @Param("custId") String custId, @Param("url") String url,
            @Param("path") String path, @Param("contractType") String contractType,
            @Param("contractName") String contractName, @Param("contractId") String contractId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CONTRACTS_INFO SET STATUS = 1, PATH = :path, SIGNED_DATE = SYSDATE WHERE ID = :id", nativeQuery = true)
    void sign(@Param("id") String id, @Param("path") String path);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE CONTRACTS_INFO SET STATUS = :status, SIGNED_DATE = SYSDATE WHERE CUSTID = :cid and CONTRACT_TYPE = '3GANG'", nativeQuery = true)
    void update(@Param("cid") String cid, @Param("status") int status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CONTRACTS_INFO SET STATUS = :status, SIGNED_DATE = SYSDATE WHERE ID = :id AND CUSTID = :custid", nativeQuery = true)
    void sended(@Param("status") int status, @Param("id") String id, @Param("custid") String custid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CONTRACTS_INFO SET STATUS = 4, PATH = :path, SIGNED_DATE = SYSDATE WHERE ID = :id", nativeQuery = true)
    void signAndSendDirectly(@Param("id") String id, @Param("path") String path);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CONTRACTS_INFO SET STATUS = 9, PATH = :path, SIGNED_DATE = SYSDATE WHERE ID = :id", nativeQuery = true)
    void signSendMultiFiles(@Param("id") String id, @Param("path") String path);

}
