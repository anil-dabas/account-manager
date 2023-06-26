package com.acmebank.accountmanager.behaviouraltest;

import com.acmebank.accountmanager.domainobject.Account;
import com.acmebank.accountmanager.repository.AccountRepository;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AccountManagerCreateAccountSteps {

    @Autowired
    AccountRepository accountRepository;

    @Given("an account with customerId {string} accountNumber {string} accountBalance {string}")
    public void check_account_balance(String customerId,String accountNumber,String accountBalance) {
        Account account = Account.builder().customerId(customerId)
                .accountNumber(accountNumber).accountBalance(new BigDecimal(accountBalance)).build();
        accountRepository.save(account);
    }

}
