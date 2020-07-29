package com.restassured;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class DeleteExample {
    @BeforeTest
    public void setup(){

        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RestAssured.basePath = "/posts/";
    }

    @Test
    public void testDelete(){
        given()
                .when()
                .delete("/1")
                .then()
                .header("Expires", equalTo("-1"));


    }
}