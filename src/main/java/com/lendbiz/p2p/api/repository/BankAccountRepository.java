package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.BankAccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bankAccountRepository")
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {

	@Query(value = "select bank_name, bank_account_name, bank_account_number, bank_code from gm_userbankinfo where custid = ?1 and state = 1", nativeQuery = true)
	BankAccountEntity getUserBankAccount(String custId);

}
