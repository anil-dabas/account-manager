package com.acmebank.accountmanager.behaviouraltest;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features" ,
        glue = "classpath:com.acmebank.accountmanager.behaviouraltest",
        publish = true,
        plugin = {"pretty","html:build/reports/cucumber"}
)
public class CucumberIntegrationTest {
}