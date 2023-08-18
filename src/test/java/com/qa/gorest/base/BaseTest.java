package com.qa.gorest.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

	protected RestClient restClient;
	protected Properties prop;
	protected ConfigManager config;
	protected String baseURI;
	
	// Service URLs
	
	public static final String GOREST_ENDPOINT = "/public/v2/users";
	public static final String AMADEUS_FLIGHTDESTINATIONS_ENDPOINT = "/v1/shopping/flight-destinations";
	public static final String CIRCUIT_ENDPOINT = "/api/f1";

	@Parameters({ "baseURI" })
	@BeforeTest
	public void setupTestData(String baseURI) {
		RestAssured.filters(new AllureRestAssured());
		this.baseURI = baseURI;
		config = new ConfigManager();
		prop = config.initProperties();
	}	

}
