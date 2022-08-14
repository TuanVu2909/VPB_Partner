package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.FirstPasswordEntity;
import com.lendbiz.p2p.api.entity.UpdateAccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateAccountRepository extends JpaRepository<UpdateAccountEntity, String> {
    @Procedure("UpdateAccountEntity.updateAccount")
    UpdateAccountEntity updateAccount(@Param("p_custId") String custId, @Param("p_fullName") String fullName,
            @Param("p_idCode") String idCode, @Param("p_sex") String sex,
            @Param("p_dob") String dob, @Param("p_address") String address,
            @Param("p_id_exp") String idExp, @Param("p_id_date") String idDate,
            @Param("p_idPlace") String idPlace,
            @Param("p_bankName") String bankName,
            @Param("p_bankAccount") String bankAccount,
            @Param("p_bankAccountName") String bankAccountName);

    @Procedure("UpdateAccountEntity.updateBankAccount")
    UpdateAccountEntity updateBankAccount(
            @Param("p_custId") String custId,
            @Param("p_bankName") String bankName,
            @Param("p_bankAccount") String bankAccount,
            @Param("p_bankAccountName") String bankAccountName);
}
