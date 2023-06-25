package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.exception.AccountDoesNotExistException;
import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.request.CreateTransactionRequest;

import java.math.BigDecimal;

public interface AccountService {
    BigDecimal getBalance(String accountNumber) throws AccountDoesNotExistException;

    TransactionDTO createTransaction(CreateTransactionRequest transactionRequest) throws AccountDoesNotExistException;
}
