
// REST API class on August 19, 2023, Saturday with Instructor MD Islam:

package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

// import io.restassured.RestAssured;   // this is non-static so we cant get all the way to given method
import static io.restassured.RestAssured.given; // this is static pkg of io restassured and has them static methods

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DeleteOneProductwithSoftAssert {

// 	Class 2 material:	

	// another way is to declare baseuri globally
	String baseURI;
	HashMap <String, String> deletePayload;
	SoftAssert softassert;
		// Only TestNG has this class while JUnit DOES NOT
		// Even if softassert fails, it will continue with rest of the codes below it
		// Vs
		// hard assert or Assert will stop the coding right there if that assertion step fails.


	public DeleteOneProductwithSoftAssert() {

		baseURI = "https://techfios.com/api-prod/api/product";
		deletePayload = new HashMap<String, String>();
		softassert = new SoftAssert();
	}
	
	public Map<String, String> deletePayloadMap(){
		
		deletePayload.put("id", "8680");
		return deletePayload;
				
		}

	@Test (priority=1)
	public void deleteOneProduct() {

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json; charset=UTF-8")   
					// charset UTF-8 makes it user friendly where you can just copy paste to get data	

			// industry standard is to use preemptive auth
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				.body(deletePayloadMap()).
//				.log().all().
			when()
				.delete("/delete.php").
			then()
//				.log().all()
				.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
//		Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!!!");
		softassert.assertEquals(statusCode, 200, "Status codes are NOT matching!!!");
		// Junit assertion: Expected then equal (JEA sounds like Jiya)
		// TestNG assertion: actual then expected (TAE sounds like thai)

		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Reponse Header: " + responseHeader);
//		Assert.assertEquals(responseHeader, "application/json; charset=UTF-9", "Response Headers are NOT matching!!!");
				// regular Assert is hard assert where if it fails, it STOPS executing the rest
		// VS
		softassert.assertEquals(responseHeader, "application/json; charset=UTF-9", "Response Headers are NOT matching!!!");
				// this even if it fails, will continue with rest of the codes below

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
		
		String deleteProductMessage = jp.get("message");
	 	System.out.println("Product delete Message:" + deleteProductMessage);
//		Assert.assertEquals(deleteProductMessage, "Product was deleted.", "Product Creation Messages are NOT matching!!!");
		softassert.assertEquals(deleteProductMessage, "Product was deleted.", "Product Creation Messages are NOT matching!!!");

	 	softassert.assertAll();
	 		// always do this to print what specific assertion has failed if any
 
	}
	
	@Test(priority=2)
	public void readDeletedProductAndCompareDetails() {

		Response response =

			given()
				.baseUri(baseURI)
				.header("Content-Type", "application/json")
				.queryParam("id", deletePayloadMap().get("id"))
		// industry standard is to use preemptive auth
				.auth().preemptive().basic("demo@techfios.com", "abc123").
//				.log().all().
			when()
				.get("/read_one.php").
			then()
//				.log().all()
				.extract().response();

		System.out.println("Product ID: " + deletePayloadMap().get("id"));
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 405, "Status codes are NOT matching!!!");    
			// intentionally mistaking status code from 404 to 405 to check if it stops running the rest of the codes or not
//		softassert.assertEquals(statusCode, 404, "Status codes are NOT matching!!!");
		String responseBody = response.getBody().asString(); // this will convert into string
		System.out.println("Response Body: " + responseBody);

		
		JsonPath jp = new JsonPath(responseBody); 
		
		String actualProductMessage = jp.get("message");
	 	System.out.println("Actual Product Message: " + actualProductMessage);
//		Assert.assertEquals(actualProductMessage, "Product does not exist.", "Product messages are NOT matching!!!");
		softassert.assertEquals(actualProductMessage, "Product does not exist.", "Product messages are NOT matching!!!");

	 	softassert.assertAll();
	 	
	}	
	
}
