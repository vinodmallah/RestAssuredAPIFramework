package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TestMainClass {

	public static void main(String[] args) {
		Map<Integer,String> testMap = new HashMap<>();
		testMap.put(2, "Rakesh");
		testMap.put(3, "Praj");
		testMap.put(4, "UT");
		
		for(Entry<Integer,String> entry : testMap.entrySet()) {
			System.out.println("Key : "+entry.getKey()+" | Value : "+entry.getValue());
		}

	}

}
