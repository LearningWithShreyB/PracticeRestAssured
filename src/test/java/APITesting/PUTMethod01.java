package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import java.util.HashMap;

public class PUTMethod01 {

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
	}

	@Test(priority = 2, dependsOnMethods = { "token" }, dependsOnGroups = { "getBooking" }, groups = {"updateUser"})
	void putMethod() {
		HashMap<String, Object> bookingDates = new HashMap<>();

		bookingDates.put("checkin", "2018-01-01");
		bookingDates.put("checkout", "2019-01-01");

		HashMap<String, Object> requestBody1 = new HashMap<>();

		requestBody1.put("firstname", "James");
		requestBody1.put("lastname", "Adam");
		requestBody1.put("totalprice", 1000);
		requestBody1.put("depositpaid", true);
		requestBody1.put("bookingdates", bookingDates);
		requestBody1.put("additionalneeds", "Lunch");

		given().contentType(ContentType.JSON).body(requestBody1).header("Cookie", "token=" + token).when()
				.put("https://restful-booker.herokuapp.com/booking/" + Postmethod01.bookingID).then().log().all()
				.statusLine(containsString("OK")).statusCode(200)
				.header("Content-Type", containsString("application/json"))
				.body("firstname", equalTo("James")).body("lastname", equalTo("Adam")).body("totalprice", equalTo(1000))
				.body("depositpaid", equalTo(true)).body("bookingdates.checkin", equalTo("2018-01-01"))
				.body("bookingdates.checkout", equalTo("2019-01-01")).body("additionalneeds", equalTo("Lunch"));
	}

}
