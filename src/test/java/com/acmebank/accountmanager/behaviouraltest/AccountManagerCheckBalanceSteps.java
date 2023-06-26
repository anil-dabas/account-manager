package com.acmebank.accountmanager.behaviouraltest;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AccountManagerCheckBalanceSteps {

    private Response response;

    @When("customer check the account balance for {string}")
    public void check_account_balance(String accountNumber) {
        String checkBalanceURL = "/account/"+accountNumber;
        response = given().port(8085).get(checkBalanceURL);
    }

    @Then("the account balance should be {string}")
    public void account_balance(String accountBalance){
        String balance = response.getBody().asString();
        assertEquals(accountBalance,balance);
        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
    }
}