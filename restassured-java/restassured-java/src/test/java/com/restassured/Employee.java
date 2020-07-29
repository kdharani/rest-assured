package com.restassured;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Employee {

    @Test(priority = 1)
    public void testStatusCode(){
        get("http://dummy.restapiexample.com/employees").then().statusCode(200);
    }

    @Test(priority = 2)
    public void testBody(){
        get("https://postman-echo.com/GET").then().assertThat()
                .body("headers.host", equalTo("postman-echo.com"));
    }

    @Test(priority = 3)
    public void testHeader(){
        get("https://postman-echo.com/GET").then()
                .header("Content-Encoding", "gzip");
    }

    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                and().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20)).
                and().
                header("Content-Length", equalTo("4567"));
    }

    @Test
    public void getEmployeeTest(){
        Response response = given().
                pathParam("employeeId", 1).
                when().
                get("http://dummy.restapiexample.com/api/v1/employee/{employeeId}").
                then().
                extract().response();

        System.out.println(response.prettyPrint());
    }

    @Test
    public void postEmployeeTest(){
        String requestBody = "{\"name\":\"test\",\"salary\":\"123\",\"age\":\"23\"}";

        given().
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("http://dummy.restapiexample.com/api/v1/create").
                then().
                statusCode(200).
                and().
                body("status",equalTo("success")).
                extract().response().prettyPrint();
    }
}
