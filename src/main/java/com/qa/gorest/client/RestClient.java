package com.qa.gorest.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.qa.gorest.exception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	private RequestSpecBuilder reqSpecBuilder;
	private String baseURI;
	private Properties prop;

	private boolean isAuthenticationHeaderAdded = false;

	public boolean isAuthenticationHeaderAdded() {
		return isAuthenticationHeaderAdded;
	}

	public void setAuthenticationHeaderAdded(boolean isAuthenticationHeaderAdded) {
		this.isAuthenticationHeaderAdded = isAuthenticationHeaderAdded;
	}

	public RestClient(Properties prop, String baseURI) {
		reqSpecBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}

	private void addAuthorizationHeader() {
		if (!isAuthenticationHeaderAdded) {
			reqSpecBuilder.addHeader("Authorization", prop.getProperty("Token"));
			isAuthenticationHeaderAdded = true;
		}
	}

	private void setRequestContentType(String contentType) {

		switch (contentType.toLowerCase()) {
		case "json":
			reqSpecBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			reqSpecBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			reqSpecBuilder.setContentType(ContentType.TEXT);
			break;
		case "urlencoding":
			reqSpecBuilder.setContentType(ContentType.URLENC);
			break;
		default:
			throw new APIFrameworkException("INVALID CONTENT TYPE");
		}

	}

	/***************** Request Spec Builder For GET API calls ********************/

	private RequestSpecification createRequestSpec() {
		reqSpecBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		return reqSpecBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap) {
		reqSpecBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		if (headersMap != null) {
			reqSpecBuilder.addHeaders(headersMap);
		}
		return reqSpecBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryParamsMap) {
		reqSpecBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		if (headersMap != null) {
			reqSpecBuilder.addHeaders(headersMap);
		}
		if (queryParamsMap != null) {
			reqSpecBuilder.addQueryParams(queryParamsMap);
		}
		return reqSpecBuilder.build();
	}

	/***************** Request Spec Builder For POST API calls ********************/

	private RequestSpecification createRequestSpec(Object requestBody, String contentType) {
		reqSpecBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		if (requestBody != null) {
			reqSpecBuilder.setBody(requestBody);
		}
		setRequestContentType(contentType);
		return reqSpecBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> headersMap) {
		reqSpecBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		setRequestContentType(contentType);
		if (headersMap != null) {
			reqSpecBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			reqSpecBuilder.setBody(requestBody);
		}
		return reqSpecBuilder.build();
	}
	
	private RequestSpecification createRequestSpec(String contentType, Map<String, String> formParams) {
		reqSpecBuilder.setBaseUri(baseURI);
		setRequestContentType(contentType);
		if (formParams != null) {
			reqSpecBuilder.addFormParams(formParams);
		}
		return reqSpecBuilder.build();
	}

	/*******************************************************************************/

	///////////////////////// HTTP Methods Utils ///////////////////////////////

	/************************ GET API calls Util Methods *************************/

	public Response get(String serviceUrl, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec()).log().all().when().log().all().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headersMap)).log().all().when().log().all().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, String> queryParamsMap,
			boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParamsMap)).log().all().when().log().all()
					.get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap)).when().get(serviceUrl);
	}

	/************************ POST API calls Util Methods *************************/

	public Response post(String serviceUrl, Object requestBody, String contentType, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all().when().log().all()
					.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType)).when().post(serviceUrl);
	}

	public Response post(String serviceUrl, Object requestBody, String contentType, Map<String, String> headersMap,
			boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all().when().log()
					.all().post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().post(serviceUrl);
	}

	
	public Response post(String serviceUrl, String contentType, Map<String, String> formParam,
			boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(contentType, formParam)).log().all().when().log()
					.all().post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(contentType, formParam)).when().post(serviceUrl);
	}
	
	
	/************************ PUT API calls Util Methods *************************/

	public Response put(String serviceUrl, Object requestBody, String contentType, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all().when().log().all()
					.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType)).when().put(serviceUrl);
	}

	public Response put(String serviceUrl, Object requestBody, String contentType, Map<String, String> headersMap,
			boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all().when().log()
					.all().put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().put(serviceUrl);
	}

	/************************
	 * PATCH API calls Util Methods
	 *************************/

	public Response patch(String serviceUrl, Object requestBody, String contentType, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all().when().log().all()
					.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType)).when().patch(serviceUrl);
	}

	public Response patch(String serviceUrl, Object requestBody, String contentType, Map<String, String> headersMap,
			boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all().when().log()
					.all().patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().patch(serviceUrl);
	}

	/************************
	 * DELETE API calls Util Methods
	 *************************/

	public Response delete(String serviceUrl, boolean log) {

		if (log) {
			return RestAssured.given(createRequestSpec()).log().all().when().log().all().delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().delete(serviceUrl);
	}

	/************************
	 * Auth2.0 Access Token - Amadeus
	 *************************/

	public String getAccessToken(String serviceUrl, String grantType, String clientID, String clientSecret) {

		Map<String,String> formParams = new HashMap<>();
		formParams.put("grant_type", grantType);
		formParams.put("client_id", clientID);
		formParams.put("client_secret", clientSecret);
		
		return post(serviceUrl,"urlencoding",formParams,true)
				.then().log().all()
					.extract().path("access_token");
	}

}
