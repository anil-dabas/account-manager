package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.exception.AccountDoesNotExistException;
import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.request.CreateTransactionRequest;
import com.acmebank.accountmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/account")
public class AccountManagerController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{account_no}")
    ResponseEntity<String> getBalance(@PathVariable(value ="account_no") String accountNumber) throws AccountDoesNotExistException {
        return new ResponseEntity<>(accountService.getBalance(accountNumber).toString(), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    ResponseEntity<TransactionDTO> createTransaction(@RequestBody CreateTransactionRequest transactionRequest) throws AccountDoesNotExistException {
        return new ResponseEntity<>(accountService.createTransaction(transactionRequest),HttpStatus.OK);
    }

}
