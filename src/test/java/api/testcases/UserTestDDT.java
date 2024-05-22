package api.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UserTestDDT {
	User userPayload;
	
	@Test(priority = 1, dataProvider = "AllData", dataProviderClass = DataProviders.class)
	public void testCreateUser(String userId, String userName, String fName, String lName, String email, String pwd, String phone) {
		
		userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(email);
		userPayload.setPassword(pwd);
		userPayload.setPhone(phone);
		
		Response response = UserEndPoints.createUser(userPayload);
		
		System.out.println("-----------------------------------testCreateUser----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
	}
	
	@Test(priority = 2, dataProvider = "UserNamesData", dataProviderClass = DataProviders.class)
	public void testDeleteUser(String userName) {
		
		Response response = UserEndPoints.deleteUser(userName);
		
		System.out.println("-----------------------------------testDeleteUser----------------------------------------");
		// log Response
		response.then().log().all();
		
		// Validation
		Assert.assertEquals(response.getStatusCode(), 200, "Check for status code");
		System.out.println("-----------------------------------------------------------------------------------------");
	}
}
