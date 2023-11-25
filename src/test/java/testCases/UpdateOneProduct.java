
// REST API class on August 19, 2023, Saturday with Instructor MD Islam:

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

// import io.restassured.RestAssured;   // this is non-static so we cant get all the way to given method
import static io.restassured.RestAssured.given; // this is static pkg of io restassured and has them static methods

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateOneProduct {

	
	
// 	Class 2 material:	

	// another way is to declare baseURI globally

	String baseURI;
	HashMap <String, String> updatePayload;


	public UpdateOneProduct() {

		baseURI = "https://techfios.com/api-prod/api/product";
		updatePayload = new HashMap<String, String>();
		
	}
	
	public Map<String, String> updatePayloadMap(){
		
		updatePayload.put("id", "8697");
		updatePayload.put("name", "Nish's update 7.0 w/ Payload");
		updatePayload.put("price", "7777777");
		updatePayload.put("description", "The best version for amazing QA peeps.");
		updatePayload.put("category_id", "2");
		updatePayload.put("category_name", "Electronics");
		return updatePayload;
			
	}

	@Test(priority=1)
	public void updateOneProductUsingPayload() {

		/*
		 * given: all input
		 * details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) when:
		 * submit api requests(Http method,Endpoint/Resource) then: validate
		 * response(status code, Headers, responseTime, Payload/Body)
		 * 
		04.UpdateOneProduct
	HTTP Method: PUT
	EndpointUrl: https://techfios.com/api-prod/api/product/update.php
	Authorization: Basic Auth/ Bearer Token
	Header: "Content-Type" : "application/json; charset=UTF-8"
	Status Code: 200
	Payload/Body: 
	{
    	"id": "4562",
    	"name": "Amazing Pillow 3.0 By MD",
    	"price": "299",
    	"description": "The updated pillow for amazing programmers.",
    	"category_id": 2
	} 
*/

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=UTF-8")   
					// charset UTF-8 makes it user friendly where you can just copy paste to get data	
				.body(updatePayloadMap())
				.auth().preemptive().basic("demo@techfios.com", "abc123").
//				.log().all().
			when()
				.put("/update.php").
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
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8", "Response Headers are NOT matching!!!");

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
		
		String updateProductMessage = jp.get("message");
	 	System.out.println("Product update Message:" + updateProductMessage);
		Assert.assertEquals(updateProductMessage, "Product was updated.", "Product Creation Messages are NOT matching!!!");
		 
	}
	
	
	@Test(priority=2)
	public void readOneProductAndCompareDetails() {

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json")
				.queryParam("id", updatePayloadMap().get("id"))
		// industry standard is to use preemtive auth
				.auth().preemptive().basic("demo@techfios.com", "abc123").
//				.log().all().
			when()
				.get("/read_one.php").
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

		String responseBody = response.getBody().asString(); // this will convert into string
		System.out.println("Response Body: " + responseBody);

		
		JsonPath jp = new JsonPath(responseBody); 
		
		String actualProductName = jp.get("name");
	 	System.out.println("Actual Product Name:" + actualProductName);
	 	
		String actualProductDescription = jp.get("description");
	 	System.out.println("Actual Description Name:" + actualProductDescription);

	 	String actualProductPrice = jp.get("price");
	 	System.out.println("Actual Product Price Name:" + actualProductPrice);
	 	
	 	
//		JsonPath jp2 = new JsonPath(updatePayloadMap().toString()); 
	 		// we dont even need this since we already have our keys and values in the map array above
		
		String expectedProductName = updatePayloadMap().get("name");
	 	System.out.println("Expected Product Name:" + expectedProductName);
	 	
		String expectedProductDescription = updatePayloadMap().get("description");
	 	System.out.println("Expected Description Name:" + expectedProductDescription);

	 	String expectedProductPrice = updatePayloadMap().get("price");
	 	System.out.println("Expected Product Price Name:" + expectedProductPrice);

	 	
		Assert.assertEquals(actualProductName, expectedProductName, "Product Names are NOT matching!!!");

		Assert.assertEquals(actualProductDescription, expectedProductDescription, "Product Descriptions are NOT matching!!!");
		
		Assert.assertEquals(actualProductPrice, expectedProductPrice, "Product Prices are NOT matching!!!");
			 
	}	
	
}
