
// REST API class on August 19, 2023, Saturday with Instructor MD Islam:

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

// import io.restassured.RestAssured;   // this is non-static so we cant get all the way to given method
import static io.restassured.RestAssured.given;   // this is static pkg of io restassured and has them static methods

import java.util.concurrent.TimeUnit;


public class ReadAllProducts {

	
	
	
	
	
	
// 	Class 2 material:	
	
	// another way is to declare baseuri globally
//	String baseURI = "https://techfios.com/api-prod/api/product/read.php";
	
	String baseURI;
	
	public ReadAllProducts() {
		
		baseURI = "https://techfios.com/api-prod/api/product/read.php";
	}
	
	
	@Test
	public void readAllProducts() {
		
		/*
		 given: all input
		 details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) when:
		 submit api requests(Http method,Endpoint/Resource) then: validate
		 response(status code, Headers, responseTime, Payload/Body)
		
		01. ReadAllProducts
		HTTP Method: GET
		EndpointUrl: https://techfios.com/api-prod/api/product/read.php
		baseURI: https://techfios.com/api-prod/api/product
		endpoint: /read.php
		Authorization:
		Basic Auth/ Bearer Token
		Header:
		"Content-Type" : "application/json; charset=UTF-8"
		Status Code: 200
		 */
		
		Response response =
		
		
		given()
			.baseUri(baseURI)
//			.baseUri("https://techfios.com/api-prod/api/product")
//			.baseUri("https://techfios.com/api-prod/api/product/read.php")
			.header("Content-Type","application/json; charset=UTF-8").
//			.log().all().
		when()
			.get("/read.php").
// 			.get("").
		then()
//			.log().all()
			.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 200, "Status code are NOT matching!!!");
		// Junit assertion: Expected then equal (JEA sounds like Jiya)
		// TestNG assertion: actual then expected (TAE sounds like thai)
			
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Reponse Header: " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8", "Response Headers are NOT matching!!!");
			
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Reponse Time: " + responseTime);
		
		if(responseTime <= 2000) {
			
			System.out.println("Reponse Time is within range.");
		}
		else {
			System.out.println("Response Time is out of range!!");
		}

//		response.getBody().toString();  // this will return a representation of a string so might not be a 100% string
				// representation means it looks like a string but it is not really really the string you're expecting
				// like you sending your friend as your representation meaning that friend may look like you, but its not actually you
		String responseBody = response.getBody().asString();  // this will convert into string
		System.out.println("Response Body: " + responseBody);
		
		
		JsonPath jp = new JsonPath(responseBody);
		String firstProductID = jp.get("records[0].id");
		String secondProductID = jp.get("records[1].id");
		String thousandthProductID = jp.get("records[999].id");
		System.out.println("First Product ID:" + firstProductID);
		
		if(firstProductID != null) {
			
			System.out.println("First Product ID is NOT null");
		}
		else {
			System.out.println("First Product ID is null");
		}
		
		
	}
	
	
	
// 	Class 1 material:
	
	/*
	@Test
	public void readAllProducts() {
		
		
		 given: all input
		 details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) when:
		 submit api requests(Http method,Endpoint/Resource) then: validate
		 response(status code, Headers, responseTime, Payload/Body)
		
		01. ReadAllProducts
		HTTP Method: GET
		EndpointUrl: https://techfios.com/api-prod/api/product/read.php
		baseURI: https://techfios.com/api-prod/api/product
		endpoint: /read.php
		Authorization:
		Basic Auth/ Bearer Token
		Header:
		"Content-Type" : "application/json; charset=UTF-8"
		Status Code: 200
		 	
		
		given()
			.baseUri(baseURI)
//			.baseUri("https://techfios.com/api-prod/api/product")
//			.baseUri("https://techfios.com/api-prod/api/product/read.php")
			.header("Content-Type","application/json; charset=UTF-8")
			.log().all().
		when()
			.get("/read.php").
// 			.get("").
		then()
			.statusCode(200)
			.header("Content-Type", "application/json; charset=UTF-8")
//			.body("Content-Type","application/json; charset=UTF-8")
			.log().all();		
	}
	*/
	
	
}
