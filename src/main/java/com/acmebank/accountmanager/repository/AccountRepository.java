package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.domainobject.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByAccountNumberIn(List<String> accountNumbers);
}
