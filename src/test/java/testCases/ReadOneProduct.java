
// REST API class on August 19, 2023, Saturday with Instructor MD Islam:

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

// import io.restassured.RestAssured;   // this is non-static so we cant get all the way to given method
import static io.restassured.RestAssured.given; // this is static pkg of io restassured and has them static methods

import java.util.concurrent.TimeUnit;

public class ReadOneProduct {

// 	Class 2 material:	

	// another way is to declare baseuri globally
//	String baseURI = "https://techfios.com/api-prod/api/product/read.php";

	String baseURI;

		// use the constructor declare these inside so you can instantiate first thing to have them ready to be used before method is invoked 
	public ReadOneProduct() {

		baseURI = "https://techfios.com/api-prod/api/product";
	}

	@Test
	public void readOneProduct() {

		/*
		 * given: all input
		 * details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) when:
		 * submit api requests(Http method,Endpoint/Resource) then: validate
		 * response(status code, Headers, responseTime, Payload/Body)
		 * 
		 * 02.ReadOneProduct HTTP Method: GET EndpointUrl:
		 * https://techfios.com/api-prod/api/product/read_one.php Authorization: Basic
		 * Auth/ Bearer Token Header:"Content-Type" : "application/json" QueryParam:
		 * (id# is required) "id":"value" Status Code: 200
		 */

		Response response =

			given()
				.baseUri(baseURI)
//				.baseUri("https://techfios.com/api-prod/api/product")
//				.baseUri("https://techfios.com/api-prod/api/product/read.php")
				.header("Content-Type", "application/json")
				.queryParam("id", "8624")
				.queryParam("price", "200") 
						// if we were to add another parameter ad its key and value																				
//				.auth().basic("demo@techfios.com", "abc123")
						// industry standard is to use preemtive auth
				.auth().preemptive().basic("demo@techfios.com", "abc123").
						// preemtive validates the username and password with the server if it is
						// required or anticipated.....
						// if server says authentication not required then just disregard it....I am
						// just going to do it anyway
//				.log().all().
			when()
				.get("/read_one.php").
// 				.get("").
			then()
//				.log().all()
				.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!!!");
		// Junit assertion: Expected then equal (JEA sounds like Jiya)
		// TestNG assertion: actual then expected (TAE sounds like thai)

		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Reponse Header: " + responseHeader);
		Assert.assertEquals(responseHeader, "application/json", "Response Headers are NOT matching!!!");

		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Reponse Time: " + responseTime);

		if (responseTime <= 2000) {

			System.out.println("Reponse Time is within range.");
		} else {
			System.out.println("Response Time is out of range!!");
		}

//		response.getBody().toString();  // this will return a representation of a string so might not be a 100% string
		// representation means it looks like a string but it is not really really the
		// string you're expecting
		// like you sending your friend as your representation meaning that friend may
		// look like you, but its not actually you
		String responseBody = response.getBody().asString(); // this will convert into string
		System.out.println("Response Body: " + responseBody);

		
		JsonPath jp = new JsonPath(responseBody); 
//		JsonPath so that we can have all the responses in 1 line, and use that
		
		String productName = jp.get("name");
	 	System.out.println("Product Name:" + productName);
		Assert.assertEquals(productName, "Amazing Pillow 8.0 By MD", "Product Names are NOT matching!!!");

		String productDescription = jp.get("description");
	 	System.out.println("Description Name:" + productName);
		Assert.assertEquals(productDescription, "The updated pillow for amazing programmers.", "Product Descriptions are NOT matching!!!");
		
		String productPrice = jp.get("price");
	 	System.out.println("Description Name:" + productPrice);
		Assert.assertEquals(productPrice, "999", "Product Prices are NOT matching!!!");
		 
	}
}
