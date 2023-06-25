package com.acmebank.accountmanager.model;

import com.acmebank.accountmanager.domainobject.Account;
import com.acmebank.accountmanager.domainvalue.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String transactionId;
    private Account fromAccount;
    private Account toAccount;
    private TransactionStatus transactionStatus;

}
