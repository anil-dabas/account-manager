package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.domainobject.Account;
import com.acmebank.accountmanager.domainobject.Transaction;
import com.acmebank.accountmanager.domainvalue.TransactionStatus;
import com.acmebank.accountmanager.exception.AccountDoesNotExistException;
import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.request.CreateTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.acmebank.accountmanager.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void getBalance() throws AccountDoesNotExistException {

        // Prepare
        Account account = Account.builder()
                .accountNumber(ACCOUNT_NUMBER_1).customerId(CUSTOMER_ID_1)
                .accountBalance(ACCOUNT_BALANCE_1).build();
        // Arrange
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        // Act-Arrange
        BigDecimal actualBalance = accountService.getBalance(ACCOUNT_NUMBER_1);
        assertEquals(0,ACCOUNT_BALANCE_1.compareTo(actualBalance));

    }

    @Test
    void getBalanceInvalidAccountNumber() {

        // Arrange
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        // Act-Arrange
        assertThrows(AccountDoesNotExistException.class, () -> accountService.getBalance(ACCOUNT_NUMBER_1));

    }

    @Test
    void createTransaction() throws AccountDoesNotExistException {

        // Prepare
        Account account1 = Account.builder().customerId(CUSTOMER_ID_1).accountNumber(ACCOUNT_NUMBER_1).accountBalance(ACCOUNT_BALANCE_1).build();
        Account account2 = Account.builder().customerId(CUSTOMER_ID_2).accountNumber(ACCOUNT_NUMBER_2).accountBalance(ACCOUNT_BALANCE_2).build();
        List<Account> accountList = List.of(account1,account2);
        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder().fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).amount(TRANSFER_AMOUNT).build();
        Transaction transaction = Transaction.builder().transactionId(TRANSACTION_ID).fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).transactionStatus(TransactionStatus.SUCCESS).build();

        // Arrange
        when(accountRepository.findByAccountNumberIn(any())).thenReturn(accountList);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // Act-Arrange
        TransactionDTO transactionDTO = accountService.createTransaction(transactionRequest);
        assertEquals(TRANSACTION_ID,transactionDTO.getTransactionId());
        assertEquals(ACCOUNT_NUMBER_1,transactionDTO.getFromAccount().getAccountNumber());
        assertEquals(0,NEW_ACCOUNT_BALANCE_1.compareTo(transactionDTO.getFromAccount().getAccountBalance()));
        assertEquals(ACCOUNT_NUMBER_2,transactionDTO.getToAccount().getAccountNumber());
        assertEquals(0,NEW_ACCOUNT_BALANCE_2.compareTo(transactionDTO.getToAccount().getAccountBalance()));
        assertEquals(TransactionStatus.SUCCESS,transactionDTO.getTransactionStatus());

    }

    @Test
    void createTransactionInvalidAccounts() {

        // Prepare
        Account account1 = Account.builder().customerId(CUSTOMER_ID_1).accountNumber(ACCOUNT_NUMBER_1).accountBalance(ACCOUNT_BALANCE_1).build();
        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder().fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).amount(TRANSFER_AMOUNT).build();

        // Arrange
        when(accountRepository.findByAccountNumberIn(any())).thenReturn(List.of(account1));

        // Act-Arrange
        assertThrows(AccountDoesNotExistException.class, () -> accountService.createTransaction(transactionRequest));

    }

    @Test
    void createTransactionWhenTheTransferAmountIsGraterThenAccountBalance() throws AccountDoesNotExistException {

        // Prepare
        Account account1 = Account.builder().customerId(CUSTOMER_ID_1).accountNumber(ACCOUNT_NUMBER_1).accountBalance(REDUCED_ACCOUNT_BALANCE_1).build();
        Account account2 = Account.builder().customerId(CUSTOMER_ID_2).accountNumber(ACCOUNT_NUMBER_2).accountBalance(ACCOUNT_BALANCE_2).build();
        List<Account> accountList = List.of(account1,account2);
        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder().fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).amount(TRANSFER_AMOUNT).build();
        Transaction transaction = Transaction.builder().transactionId(TRANSACTION_ID).fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).transactionStatus(TransactionStatus.FAILURE).build();

        // Arrange
        when(accountRepository.findByAccountNumberIn(any())).thenReturn(accountList);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // Act-Arrange
        TransactionDTO transactionDTO = accountService.createTransaction(transactionRequest);
        assertEquals(TRANSACTION_ID,transactionDTO.getTransactionId());
        assertEquals(ACCOUNT_NUMBER_1,transactionDTO.getFromAccount().getAccountNumber());
        assertEquals(0,REDUCED_ACCOUNT_BALANCE_1.compareTo(transactionDTO.getFromAccount().getAccountBalance()));
        assertEquals(ACCOUNT_NUMBER_2,transactionDTO.getToAccount().getAccountNumber());
        assertEquals(0,ACCOUNT_BALANCE_2.compareTo(transactionDTO.getToAccount().getAccountBalance()));
        assertEquals(TransactionStatus.FAILURE,transactionDTO.getTransactionStatus());

    }
}