Feature:  Account Operation Check Account Balance
  Background:
    Given an account with customerId 'CUST1234' accountNumber '1234' accountBalance '10000'

  Scenario:
    When customer check the account balance for '1234'
    Then the account balance should be '10000.00'