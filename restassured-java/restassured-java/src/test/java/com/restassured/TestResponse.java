package com.restassured;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class TestResponse {
    @BeforeTest
    public void setup(){

        RestAssured.baseURI = "https://postman-echo.com";
        RestAssured.basePath = "/get";
    }

    //http://dummy.restapiexample.com/
    @Test
    public void testResponse(){
        RestAssured.baseURI = "https://jjjjjjj-home.atlassian.net/";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("kkkkkkkkkk.kkkkkkkkkk@gmail.com");
        authScheme.setPassword("lllllllll8U1XLsXZ02927");
        RestAssured.authentication = authScheme;

        Response response =
                given().
                        when().
                        get().
                        then().contentType("application/json").
                        body("headers.x-forwarded-port", equalTo("443")).
                        extract().
                        response();

        String stringResponse = response.path("headers.x-forwarded-proto");
        System.out.println(response.getBody().prettyPrint());

        String nextURI = response.path("url");
        get(nextURI)
                .then()
                .body("x-amzn-trace-id", equalTo("Root=1-5f105106-e1ea12d7ca160d1142f9b344"));
    }
}