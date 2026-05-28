package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

import java.util.HashMap;

public class Postmethod01 {
	
	public static int bookingID;

	@Test(groups = {"createBooking"})
	void postUser() {
		HashMap<String, Object> bookingDates = new HashMap<>();

		bookingDates.put("checkin", "2018-01-01");
		bookingDates.put("checkout", "2019-01-01");

		HashMap<String, Object> requestBody = new HashMap<>();

		requestBody.put("firstname", "Jim");
		requestBody.put("lastname", "Brown");
		requestBody.put("totalprice", 111);
		requestBody.put("depositpaid", true);
		requestBody.put("bookingdates", bookingDates);
		requestBody.put("additionalneeds", "Breakfast");

		bookingID=given().contentType(ContentType.JSON).body(requestBody).when()
				.post("https://restful-booker.herokuapp.com/booking")
				.then()
				.log().all()
				.statusLine(containsString("OK")).statusCode(200).header("Content-Type", containsString("application/json"))
				.body("booking.firstname", equalTo("Jim")).body("booking.lastname", equalTo("Brown"))
				.body("booking.totalprice", equalTo(111)).body("booking.depositpaid", equalTo(true))
				.body("booking.bookingdates.checkin", equalTo("2018-01-01"))
				.body("booking.bookingdates.checkout", equalTo("2019-01-01"))
				.body("booking.additionalneeds", equalTo("Breakfast")).time(lessThan(20000L))
				.extract().jsonPath().getInt("bookingid");
		
		System.out.println("Booking ID ===> "+bookingID);
	}

}
