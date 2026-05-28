package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GETMethod01 {

	@Test(dependsOnGroups = {"createBooking"})
	void getMethod() {
		given().when().get("https://restful-booker.herokuapp.com/booking/" + Postmethod01.bookingID).then().log().all()
				.statusLine(containsString("OK")).statusCode(200)
				.header("Content-Type", containsString("application/json")).time(lessThan(20000L))
				.body("firstname", equalTo("Jim")).body("lastname", equalTo("Brown")).body("totalprice", equalTo(111))
				.body("depositpaid", equalTo(true)).body("bookingdates.checkin", equalTo("2018-01-01"))
				.body("bookingdates.checkout", equalTo("2019-01-01")).body("additionalneeds", equalTo("Breakfast"));
	}

}
