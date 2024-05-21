package api.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User userPayload;
	
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
	}
	
	@Test(priority = 1)
	public void testCreateUser() {
		Response response = UserEndPoints.createUser(userPayload);
		
		System.out.println("-----------------------------------testCreateUser----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
	}
	
	@Test(priority = 2)
	public void testGetUserData() {
		Response response = UserEndPoints.getUser(this.userPayload.getUsername());
		
		System.out.println("-----------------------------------testGetUserData----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		userPayload.setFirstName(faker.name().firstName());
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		
		System.out.println("-----------------------------------testUpdateUser----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
		
		// Read user data to check if firstname is updated or not
		Response responsePostUpdate = UserEndPoints.getUser(this.userPayload.getUsername());
		
		System.out.println("-------------------------------After Update User Data------------------------------------");
		// log Response
		responsePostUpdate.then().log().all();
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		
		System.out.println("-----------------------------------testDeleteUser----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
		System.out.println("-----------------------------------------------------------------------------------------");
	}
}
