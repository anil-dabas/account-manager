package com.acmebank.accountmanager.behaviouraltest;

import com.acmebank.accountmanager.model.TransactionDTO;
import com.acmebank.accountmanager.request.CreateTransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AccountManagerCreateTransactionSteps {


    private Response response;

    @When("customer create transfer of {string} from account {string} to account {string}")
    public void check_account_balance(String amount,String fromAccountNumber,String toAccountNumber) throws JsonProcessingException {
        String createTransactionURL = "/account/transaction";
        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder()
                .fromAccountNumber(fromAccountNumber).toAccountNumber(toAccountNumber).amount(new BigDecimal(amount)).build();
        String transactionRequestJson = new ObjectMapper().writeValueAsString(transactionRequest);
        response = given().port(8085).contentType(ContentType.JSON).body(transactionRequestJson).post(createTransactionURL); //RestAssured.post(createTransactionURL,transactionRequest);
    }

    @Then("transaction should be {string} AND fromAccountBalance {string} and toAccountBalance is {string}")
    public void transaction_status(String status,String fromAccountBalance, String toAccountBalance){
        TransactionDTO transaction = response.getBody().as(TransactionDTO.class);
        assertEquals(status,transaction.getTransactionStatus().name());
        assertEquals(fromAccountBalance,transaction.getFromAccount().getAccountBalance().toString());
        assertEquals(toAccountBalance,transaction.getToAccount().getAccountBalance().toString());
        assertEquals(HttpStatus.CREATED.value(),response.getStatusCode());
    }

}
