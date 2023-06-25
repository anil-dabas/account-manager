
-- Creating the ACCOUNT table if does not exist

CREATE TABLE IF NOT EXISTS ACCOUNT (
  customer_id VARCHAR(250) PRIMARY KEY,
  account_number VARCHAR(250) NOT NULL,
  account_balance NUMERIC(20,4) NOT NULL
);


-- Creating the TRANSACTION table if does not Exist

CREATE TABLE IF NOT EXISTS TRANSACTION (
  transaction_id VARCHAR(250) PRIMARY KEY,
  from_account_number VARCHAR(250) NOT NULL,
  to_account_number VARCHAR(250) NOT NULL,
  transaction_status VARCHAR(10) NOT NULL
);

-- Inserting the values in the ACCOUNT table

INSERT INTO ACCOUNT (customer_id, account_number, account_balance) VALUES ('1', '12345678', 1000000.00);
INSERT INTO ACCOUNT (customer_id, account_number, account_balance) VALUES ('2', '88888888', 1000000.00);