package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.exception.APIFrameworkException;

public class ConfigManager {

	public Properties initProperties() {

		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(getPropertiesFileBasedOnEnv())) {
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}

	private String getPropertiesFileBasedOnEnv() {

		String propertiesPath = null;

		String envName = System.getProperty("env");

		if (envName == null) {
			propertiesPath = ".\\src\\test\\resources\\config\\qa.config.properties";
		} else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				propertiesPath = ".\\src\\test\\resources\\config\\qa.config.properties";
				break;
			case "uat":
				propertiesPath = ".\\src\\test\\resources\\config\\uat.config.properties";
				break;
			case "preprod":
				propertiesPath = ".\\src\\test\\resources\\config\\preprod.config.properties";
				break;
			case "prod":
				propertiesPath = ".\\src\\test\\resources\\config\\config.properties";
				break;
			default:
				throw new APIFrameworkException("Environment Not Provided. Please provide environment to run test.");
			}
		}
		return propertiesPath;
	}

}
