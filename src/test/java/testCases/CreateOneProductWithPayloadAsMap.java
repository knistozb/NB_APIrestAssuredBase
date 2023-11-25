
// REST API class on August 19, 2023, Saturday with Instructor MD Islam:

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
// import io.restassured.RestAssured;   // this is non-static so we cant get all the way to given method
import static io.restassured.RestAssured.given; // this is static pkg of io restassured and has them static methods

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateOneProductWithPayloadAsMap {

	
	
	
	// go back to the past recording and learn the concept of generating and using access token.
	// also learn API concept 
	
	
	
	// ============= =================== ================== ===================== 
	
	
	
// 	Class 2 material:	

	// another way is to declare baseURI globally
//	String baseURI = "https://techfios.com/api-prod/api/product/read.php";

	String baseURI;
//	String filePath;
	String firstProductID;
	HashMap <String, String> createPayload;

	public CreateOneProductWithPayloadAsMap() {

		baseURI = "https://techfios.com/api-prod/api/product";
//		filePath = "src/main/java/data/CreatePayload.json";
		createPayload = new HashMap<String, String>();
		
	}
	
	public Map<String, String> createPayloadMap(){
		
		createPayload.putIfAbsent("name", "Nish's goodie 9.0 w/ Payload ");
		createPayload.putIfAbsent("price", "7777777");
		createPayload.putIfAbsent("description", "The best pillow for amazing programmers.");
		createPayload.putIfAbsent("category_id", "2");
		createPayload.putIfAbsent("category_name", "Electronics");
		return createPayload;
			
	}
	

	@Test (priority=1)
	public void createOneProduct() {

		/*
		 * given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) 
		 * when: submit api requests(Http method,Endpoint/Resource) 
		 * then: validate response(status code, Headers, responseTime, Payload/Body)
		 * 
		 03.CreateOneProduct
		HTTP Method: POST
		EndpointUrl: https://techfios.com/api-prod/api/product/create.php
		Authorization: Basic Auth/ Bearer Token
		Header:"Content-Type" : "application/json; charset=UTF-8"
		Status Code: 201
		Payload/Body: 
		{
    		"name": "Amazing Pillow 2.0 By MD",
    		"price": "199",
    		"description": "The best pillow for amazing programmers.",
    		"category_id": "2",
    		"catefory_name":"Electronics"
		}
		 */

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=UTF-8")
				.body(createPayloadMap())
				.header("Authorization", "Bearer hfkjhuiHBRFUYH98347598h43857497")				
				// industry standard is to use preemptive auth
				.auth().preemptive().basic("demo@techfios.com", "abc123").
//				.log().all().
			when()
				.post("/create.php").
			then()
//				.log().all()
				.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 201, "Status codes are NOT matching!!!");

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
		// representation means it looks like a string but it is not really really the string you're expecting
		// like you sending your friend as your representation meaning that friend may look like you, but its not actually you
		String responseBody = response.getBody().asString(); // this will convert into real/actual string
		System.out.println("Response Body: " + responseBody);

		
		JsonPath jp = new JsonPath(responseBody); 
//		JsonPath so that we can have all the responses in 1 line, and use that
		
		String productCreationMessage = jp.get("message");
	 	System.out.println("Product Creation Message:" + productCreationMessage);
		Assert.assertEquals(productCreationMessage, "Product was created.", "Product Creation Messages are NOT matching!!!");
	 
	}
	
	@Test (priority=2)
	public void readAllProducts() {
		
		Response response =
			given()
				.baseUri(baseURI)
				.header("Content-Type","application/json; charset=UTF-8").
//				.log().all().
			when()
				.get("/read.php").
			then()
//				.log().all()
				.extract().response();

		String responseBody = response.getBody().asString();  // this will convert into string
		System.out.println("Response Body: " + responseBody);
		
		
		JsonPath jp = new JsonPath(responseBody);
		firstProductID = jp.get("records[0].id");
		System.out.println("First Product ID:" + firstProductID);
		
		if(firstProductID != null) {
			
			System.out.println("First Product ID is NOT null");
		}
		else {
			System.out.println("First Product ID is null");
		}
			
	}
	
	@Test (priority=3)
	public void readOneProductAndCompareDetails() {

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json")
				.queryParam("id", firstProductID)
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
	 	
	 	
//		JsonPath jp2 = new JsonPath(createPayloadMap().toString()); 
	 		// we dont even need this since we already have our keys and values in the map array above
		
		String expectedProductName = createPayloadMap().get("name");
	 	System.out.println("Expected Product Name:" + expectedProductName);
	 	
		String expectedProductDescription = createPayloadMap().get("description");
	 	System.out.println("Expected Description Name:" + expectedProductDescription);

	 	String expectedProductPrice = createPayloadMap().get("price");
	 	System.out.println("Expected Product Price Name:" + expectedProductPrice);

	 	
		Assert.assertEquals(actualProductName, expectedProductName, "Product Names are NOT matching!!!");

		Assert.assertEquals(actualProductDescription, expectedProductDescription, "Product Descriptions are NOT matching!!!");
		
		Assert.assertEquals(actualProductPrice, expectedProductPrice, "Product Prices are NOT matching!!!");
			 
	}
	
}
