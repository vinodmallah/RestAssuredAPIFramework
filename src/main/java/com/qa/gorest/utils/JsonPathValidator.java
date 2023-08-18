package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.exception.APIFrameworkException;

import io.restassured.response.Response;

public class JsonPathValidator {
	
	private String getJsonResponseAsString(Response response) {
		return  response.getBody().asString();
	}

	public <T> T read(Response response, String jsonPath) {
		try {
			return JsonPath.read(getJsonResponseAsString(response), jsonPath);
		} catch (PathNotFoundException e) {
			throw new APIFrameworkException(e.getMessage());
		}
	}
	
	public <T> List<T> readList(Response response, String jsonPath) {
		try {
			return JsonPath.read(getJsonResponseAsString(response), jsonPath);
		} catch (PathNotFoundException e) {
			throw new APIFrameworkException(e.getMessage());
		}
	}
	
	public <T> List<Map<String,T>> readListOfMaps(Response response, String jsonPath) {
		try {
			return JsonPath.read(getJsonResponseAsString(response), jsonPath);
		} catch (PathNotFoundException e) {
			throw new APIFrameworkException(e.getMessage());
		}
	}

}
