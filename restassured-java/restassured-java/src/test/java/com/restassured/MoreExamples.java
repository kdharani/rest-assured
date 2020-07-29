package com.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

public class MoreExamples {


    //Reusing checks with ResponseSpecBuilder
    @Test
    public void responseBuilderExampleTest() {
        ResponseSpecification checkStatusCodeAndContentType =
            new ResponseSpecBuilder().
                    expectStatusCode(200).
                    expectContentType(ContentType.JSON).

                    build();
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().

                spec(checkStatusCodeAndContentType).
                and().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }


    //Passing parameters between tests

    @Test
    public void passingParametersBetweenTest() {

        // First, retrieve the circuit ID for the first circuit of the 2017 season
        String circuitId = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[0]");

        // Then, retrieve the information known for that circuit and verify it is located in Australia
        given().
                pathParam("circuitId",circuitId).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location[0].country",equalTo("Australia"));
    }

    //Accessing secured APIs

    @Test
    public void accessingSecuredAPITest() {

        given().
                auth().
                preemptive().
                basic("username", "password").
                when().
                get("http://path.to/basic/secured/api").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void test_APIWithOAuth2Authentication_ShouldBeGivenAccess() {

        given().
                auth().
                oauth2("YOUR_AUTHENTICATION_TOKEN_GOES_HERE").
                when().
                get("http://path.to/oath2/secured/api").
                then().
                assertThat().
                statusCode(200);
    }


    // Query and Path Parameters

    @Test
    public void queryParameterTest() {

        String originalText = "test";
        String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";

        given().
                param("text",originalText).
                when().
                get("http://md5.jsontest.com/").
                then().
                assertThat().
                body("md5",equalTo(expectedMd5CheckSum));
    }

    @Test
    public void pathParameterTest() {

        String season = "2017";
        int numberOfRaces = 20;

        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
    }


}
