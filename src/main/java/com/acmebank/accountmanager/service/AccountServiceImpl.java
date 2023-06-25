package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.domainobject.Account;
import com.acmebank.accountmanager.domainobject.Transaction;
import com.acmebank.accountmanager.domainvalue.TransactionStatus;
import com.acmebank.accountmanager.exception.AccountDoesNotExistException;
import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.request.CreateTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public BigDecimal getBalance(String accountNumber) throws AccountDoesNotExistException{
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(AccountDoesNotExistException :: new);
        return account.getAccountBalance();
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(CreateTransactionRequest transactionRequest) throws AccountDoesNotExistException {
        // Getting Accounts for Transaction
        List<Account> accountDOs = accountRepository.findByAccountNumberIn(List.of(transactionRequest.getFromAccountNumber(),transactionRequest.getToAccountNumber()));

        // Accounts validity check
        if(accountDOs.size() != 2)
            throw  new AccountDoesNotExistException();

        // From and To accounts
        Account fromAccount = accountDOs.stream().filter(account -> transactionRequest.getFromAccountNumber().equals(account.getAccountNumber())).findFirst().get();
        Account toAccount = accountDOs.stream().filter(account -> transactionRequest.getToAccountNumber().equals(account.getAccountNumber())).findFirst().get();

        Transaction transaction = Transaction.builder()
                .fromAccountNumber(transactionRequest.getFromAccountNumber())
                .toAccountNumber(transactionRequest.getToAccountNumber())
                .build();

        if(checkTransactionEligibility(fromAccount,transactionRequest.getAmount())){
            fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(transactionRequest.getAmount()));
            toAccount.setAccountBalance(toAccount.getAccountBalance().add(transactionRequest.getAmount()));
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            accountRepository.saveAll(List.of(fromAccount,toAccount));
        }else{
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }

        transaction = transactionRepository.save(transaction);

        return TransactionDTO.builder().transactionId(transaction.getTransactionId())
                .toAccount(toAccount).fromAccount(fromAccount).transactionStatus(transaction.getTransactionStatus()).build();
    }

    private boolean checkTransactionEligibility(Account fromAccount,BigDecimal transferAmount) {
        return transferAmount.compareTo(fromAccount.getAccountBalance()) <= 0;
    }
}
