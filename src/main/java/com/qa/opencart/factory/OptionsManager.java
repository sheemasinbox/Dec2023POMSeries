package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	//Do not create the methods as Static as it will hinder the parallel execution of tests
	Properties prop;
	ChromeOptions co;
	FirefoxOptions fo;
	EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions chromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running Chrome in headless mode");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("Running Chrome in incognito mode");
			co.addArguments("--incognito");
		}
		
		return co;
	}
	
	public FirefoxOptions firefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running Firefox in headless mode");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("Running Firefox in incognito mode");
			co.addArguments("--incognito");
		}
		return fo;
	}
	
	public EdgeOptions edgeOptions() {
		eo = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running Edge in headless mode");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("Running Edge in incognito mode");
			co.addArguments("--inprivate");
		}
		return eo;
	}
	
}
