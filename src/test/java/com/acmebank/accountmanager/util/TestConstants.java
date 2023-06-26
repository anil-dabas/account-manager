package com.acmebank.accountmanager.util;

import java.math.BigDecimal;

public interface TestConstants {


    String ACCOUNT_NUMBER_1 = "11111111";
    String ACCOUNT_NUMBER_2 = "22222222";
    String CUSTOMER_ID_1 = "CUST_11111111";
    String CUSTOMER_ID_2 = "CUST_22222222";
    BigDecimal ACCOUNT_BALANCE_1 = new BigDecimal(1000000);
    BigDecimal REDUCED_ACCOUNT_BALANCE_1 = new BigDecimal(100);
    BigDecimal NEW_ACCOUNT_BALANCE_1 = new BigDecimal(999000);
    BigDecimal ACCOUNT_BALANCE_2 = new BigDecimal(1000000);
    BigDecimal NEW_ACCOUNT_BALANCE_2 = new BigDecimal(1001000);
    BigDecimal TRANSFER_AMOUNT = new BigDecimal(1000);
    String TRANSACTION_ID = "TRANS_1234";
}
