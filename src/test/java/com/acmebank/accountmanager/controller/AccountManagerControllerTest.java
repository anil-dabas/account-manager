package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.domainobject.Account;
import com.acmebank.accountmanager.domainvalue.TransactionStatus;
import com.acmebank.accountmanager.exception.AccountDoesNotExistException;
import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.request.CreateTransactionRequest;
import com.acmebank.accountmanager.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.acmebank.accountmanager.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class AccountManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Test
    void getBalance() throws Exception {

        // Arrange
        when(accountService.getBalance(anyString())).thenReturn(ACCOUNT_BALANCE_1);

        // Act-Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/account/" + ACCOUNT_NUMBER_1 )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(ACCOUNT_BALANCE_1.toString()));
    }

    @Test
    void getBalanceInvalidAccount() throws Exception {

        // Arrange
        when(accountService.getBalance(anyString())).thenThrow(AccountDoesNotExistException.class);

        // Act-Assert
        Throwable exception = assertThrows(AccountDoesNotExistException.class, () -> accountService.getBalance(ACCOUNT_NUMBER_1));
        assertNotNull(exception);

    }

    @Test
    void createTransaction() throws Exception {
        // Prepare Data
        TransactionDTO transactionDTO = TransactionDTO.builder().transactionId(TRANSACTION_ID)
                .transactionStatus(TransactionStatus.SUCCESS)
                .fromAccount(Account.builder().customerId(CUSTOMER_ID_1).accountNumber(ACCOUNT_NUMBER_1).accountBalance(NEW_ACCOUNT_BALANCE_1).build())
                .toAccount(Account.builder().customerId(CUSTOMER_ID_2).accountNumber(ACCOUNT_NUMBER_2).accountBalance(NEW_ACCOUNT_BALANCE_2).build())
                .build();

        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder().amount(TRANSFER_AMOUNT)
                .fromAccountNumber(ACCOUNT_NUMBER_1).toAccountNumber(ACCOUNT_NUMBER_2).build();

        // Arrange
        when(accountService.createTransaction(any())).thenReturn(transactionDTO);
        String transactionRequestJson = new ObjectMapper().writeValueAsString(transactionRequest);

        //Act-Arrange
        mockMvc.perform(MockMvcRequestBuilders.post("/account/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionStatus").value(TransactionStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.transactionId").value(TRANSACTION_ID))
                .andExpect(jsonPath("$.fromAccount.accountNumber").value(ACCOUNT_NUMBER_1))
                .andExpect(jsonPath("$.fromAccount.accountBalance").value(NEW_ACCOUNT_BALANCE_1))
                .andExpect(jsonPath("$.toAccount.accountNumber").value(ACCOUNT_NUMBER_2))
                .andExpect(jsonPath("$.toAccount.accountBalance").value(NEW_ACCOUNT_BALANCE_2));


    }

}