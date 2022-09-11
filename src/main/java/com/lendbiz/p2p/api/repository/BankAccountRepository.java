package com.lendbiz.p2p.api.repository;

import com.lendbiz.p2p.api.entity.BankAccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("bankAccountRepository")
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {

	@Query(value = "select f.bankname, f.bankacname, f.bankacc, f.bankcode  from cfmast c, cfotheracc f where c.custid = f.cfcustid and c.custid = ?1 and f.bankcode is not null", nativeQuery = true)
	BankAccountEntity getUserBankAccount(String custId);

}
