package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
//import io.restassured.http.ContentType;
//import java.util.HashMap;

public class DELETEMethod01 {

	//public static String token;

	/*
	@Test(priority = 1)
	void token() {
		HashMap<String, String> tokenCred = new HashMap<>();

		tokenCred.put("username", "admin");
		tokenCred.put("password", "password123");

		token = given().contentType(ContentType.JSON).body(tokenCred).when()
				.post("https://restful-booker.herokuapp.com/auth").then().log().all().extract().jsonPath()
				.getString("token");

		System.out.println("Token ===> " + token);
	}*/

	@Test(/*priority = 2, dependsOnMethods = { "token" },*/ dependsOnGroups = { "getBooking1" }, groups = {"deleteUser"})
	void putMethod() {

		given().header("Cookie", "token=" + PUTMethod01.token).when()
				.delete("https://restful-booker.herokuapp.com/booking/" + Postmethod01.bookingID).then().log().all()
				.statusLine(containsString("HTTP/1.1 201 Created")).statusCode(201)
				.header("Content-Type", containsString("text/plain"))
				.body(equalTo("Created"));
	}

}
