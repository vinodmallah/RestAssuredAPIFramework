package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.UserPojo;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest {

	@BeforeMethod
	public void setupRestClient() {
		restClient = new RestClient(prop, baseURI);
	}

	@DataProvider
	public Object[][] getUserTestData() {
		return new Object[][] { { "Vinod Mallah", "male", "inactive" }, { "Prajakta", "female", "active" },
				{ "Ragga", "male", "inactive" } };
	}

	@Test(dataProvider = "getUserTestData")
	public void createUserTest(String name, String gender, String status) {

		UserPojo userObj = new UserPojo(name, StringUtils.getRandomEmailID(), gender, status);

		Integer newUserID = restClient.post(GOREST_ENDPOINT, userObj, "JSON", true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.CREATED_201.getCode()).extract().path("id");

		restClient.get(GOREST_ENDPOINT + "/" + newUserID, true).then().assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode()).and().body("id", equalTo(newUserID));
	}	

}
