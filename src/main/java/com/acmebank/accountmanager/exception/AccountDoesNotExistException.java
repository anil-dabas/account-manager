package com.acmebank.accountmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="Invalid account number or account does not exist")
public class AccountDoesNotExistException extends Exception{
    static final long serialVersionUID = 123456789L;
}
