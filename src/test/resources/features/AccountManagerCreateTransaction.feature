Feature:  Account Operation Check Account Balance
  Background:
    Given an account with customerId 'CUST1111' accountNumber '1111' accountBalance '10000'
    Given an account with customerId 'CUST2222' accountNumber '2222' accountBalance '10000'
  Scenario:
    When customer create transfer of '1000' from account '1111' to account '2222'
    Then transaction should be 'SUCCESS' AND fromAccountBalance '9000.00' and toAccountBalance is '11000.00'
  Scenario:
    When customer create transfer of '10000' from account '1111' to account '2222'
    Then transaction should be 'FAILURE' AND fromAccountBalance '9000.00' and toAccountBalance is '11000.00'