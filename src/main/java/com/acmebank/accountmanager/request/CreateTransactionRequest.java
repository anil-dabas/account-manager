package com.acmebank.accountmanager.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
}
