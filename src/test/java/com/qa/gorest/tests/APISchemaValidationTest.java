package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.UserPojo;
import com.qa.gorest.utils.StringUtils;

public class APISchemaValidationTest extends BaseTest {

	@BeforeMethod
	public void setupRestClient() {
		restClient = new RestClient(prop, baseURI);
	}

	@Test
	public void createUserSchemaValidationTest() {

		UserPojo userObj = new UserPojo("Praj", StringUtils.getRandomEmailID(), "female", "inactive");

		restClient.post(GOREST_ENDPOINT, userObj, "JSON", true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.CREATED_201.getCode())
				.body(matchesJsonSchemaInClasspath("createNewUserJSONSchema.json"));

	}

}
