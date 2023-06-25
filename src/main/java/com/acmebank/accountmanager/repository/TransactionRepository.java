package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.domainobject.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {
}
