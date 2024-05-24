package api.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest2 {

	Faker faker;
	User userPayload;
	public static Logger logger;

	@BeforeClass
	public void generateTestData() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		// Obtain Logger
		logger = LogManager.getLogger("RestAssuredAutomationFramework_test");
	}

	@Test(priority = 1)
	public void testCreateUser() {
		Response response = UserEndPoints2.createUser(userPayload);

		System.out.println("-----------------------------------testCreateUser----------------------------------------");
		// log Response
		response.then().log().all();

		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");

		// log
		logger.info("Create User Executed.");
	}

	@Test(priority = 2)
	public void testGetUserData() {
		Response response = UserEndPoints2.getUser(this.userPayload.getUsername());

		System.out.println("-----------------------------------testGetUserData----------------------------------------");
		// log Response
		response.then().log().all();

		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");

		// log
		logger.info("Get User Data Executed.");
	}

	@Test(priority = 3)
	public void testUpdateUser() {
		userPayload.setFirstName(faker.name().firstName());
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);

		System.out.println("-----------------------------------testUpdateUser----------------------------------------");
		// log Response
		response.then().log().all();

		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");

		// Read user data to check if firstname is updated or not
		Response responsePostUpdate = UserEndPoints2.getUser(this.userPayload.getUsername());

		System.out.println("-------------------------------After Update User Data------------------------------------");
		// log Response
		responsePostUpdate.then().log().all();

		// log
		logger.info("Update User Executed.");
	}

	@Test(priority = 4)
	public void testDeleteUser() {
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());

		System.out.println("-----------------------------------testDeleteUser----------------------------------------");
		// log Response
		response.then().log().all();

		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
		System.out.println("-----------------------------------------------------------------------------------------");

		// log
		logger.info("Delete User Executed.");
	}
}
