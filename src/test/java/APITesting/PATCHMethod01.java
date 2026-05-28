package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import java.util.HashMap;

public class PATCHMethod01 {

	/*
	public static String token;

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

	@Test(/*priority = 2, dependsOnMethods = { "token" },*/ dependsOnGroups = { "getBooking1" }, groups = {"updateUser1"})
	void patchMethod() {

		HashMap<String, Object> requestBody1 = new HashMap<>();

		requestBody1.put("firstname", "David");
		requestBody1.put("lastname", "Mehra");

		given().contentType(ContentType.JSON).body(requestBody1).header("Cookie", "token=" + PUTMethod01.token).when()
				.patch("https://restful-booker.herokuapp.com/booking/" + Postmethod01.bookingID).then().log().all()
				.statusLine(containsString("OK")).statusCode(200)
				.header("Content-Type", containsString("application/json"))
				.body("firstname", equalTo("David")).body("lastname", equalTo("Mehra")).body("totalprice", equalTo(1000))
				.body("depositpaid", equalTo(true)).body("bookingdates.checkin", equalTo("2018-01-01"))
				.body("bookingdates.checkout", equalTo("2019-01-01")).body("additionalneeds", equalTo("Lunch"));
	}

}
