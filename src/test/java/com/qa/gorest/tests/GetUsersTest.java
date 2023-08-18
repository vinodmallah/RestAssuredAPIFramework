package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class GetUsersTest extends BaseTest{
	
	@BeforeMethod
	public void setupRestClient() {	
		restClient = new RestClient(prop, baseURI);
	}
		
	@Test(priority=3)
	public void getAllUserTest() {
		restClient.get(GOREST_ENDPOINT, true)
							.then().log().all()
								.assertThat()
									.statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(priority=1)
	public void getSpecificUserWithQueryParamsTest() {
		
		Map<String,String> queryMap = new HashMap<>();
		queryMap.put("name", "Vinod");
		queryMap.put("status", "active");
		
		restClient.get(GOREST_ENDPOINT, null, queryMap, true)
							.then().log().all()
								.assertThat()
									.statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(priority=2)
	public void getSpecificUserTest() {
		restClient.get(GOREST_ENDPOINT +"/4432368", true)
							.then().log().all()
								.assertThat()
									.statusCode(APIHttpStatus.OK_200.getCode())
										.and()
											.body("id", equalTo(4432368))
												.and()
													.body("name",equalTo("Girja Naik"));
	}

}
