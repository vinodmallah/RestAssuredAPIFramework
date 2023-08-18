package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	
	private String accessToken;
	
	@Parameters({"baseURI","grantType","clientID","clientSecret"})
	@BeforeMethod
	public void setupRestClient(String baseURI, String grantType, String clientID, String clientSecret) {		
		restClient = new RestClient(prop, baseURI);		
		accessToken = restClient.getAccessToken("/v1/security/oauth2/token", grantType, clientID, clientSecret);
		System.out.println("Access Token : "+accessToken);
	}
	
	@Test
	public void getFlightDetailsInfoTest() {
		
		restClient = new RestClient(prop, baseURI);	
		restClient.setAuthenticationHeaderAdded(true);
		
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Authorization", "Bearer "+accessToken);
		
		Map<String,String> queryMap = new HashMap<>();
		queryMap.put("origin", "PAR");
		queryMap.put("maxPrice", "200");		
		
		Response flightDataResponse = restClient.get(AMADEUS_FLIGHTDESTINATIONS_ENDPOINT, headerMap, queryMap, true)
													.then().log().all()
															.assertThat() 
																.statusCode(APIHttpStatus.OK_200.getCode())
																	.and()
																		.extract().response();
		
		flightDataResponse.prettyPrint();
		
		
		JsonPath js = flightDataResponse.jsonPath();
		
		List<String> flightOrigin = js.getList("data.origin",String.class);	
		List<String> flightDestination = js.getList("data.destination",String.class);
		List<Float> flightPrice = js.getList("data.price.total",Float.class);
		List<String> responseDataSize = js.getList("data.type",String.class);
		
		System.out.println("Flight Origin   |  Flight Destination  | Flight Price  |");
		for(int i = 0; i<responseDataSize.size();i++) {
			System.out.println(flightOrigin.get(i)+"             |  "+flightDestination.get(i)+"                 |  "+ flightPrice.get(i)+"       |");
		}		
		
	} 

}
