package APITesting;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GETMethod4 {

	@Test(dependsOnGroups = {"deleteUser2"})
	void getMethod() {
		given().when().get("https://restful-booker.herokuapp.com/booking/" + Postmethod01.bookingID).then().log().all()
		.statusLine(containsString("404 Not Found")).statusCode(404)
		.header("Content-Type", containsString("text/plain"))
		.body(equalTo("Not Found"));
	}

}
