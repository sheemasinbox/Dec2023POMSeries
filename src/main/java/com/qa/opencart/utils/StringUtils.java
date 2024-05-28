package com.qa.opencart.utils;

import java.util.UUID;

public class StringUtils {
	
	private StringUtils() { }     //private dummy constructor to prevent object creation of this class
	
	//Random Email Generator
	public static String getRandomEmailId() {
		return "testautomation "+ System.currentTimeMillis()+ "@opencart.com";
	}
	
	public static String getRandomEmailUUId() {
		return "testautomation"+UUID.randomUUID()+"@"+"opencart.com";
	}

}
