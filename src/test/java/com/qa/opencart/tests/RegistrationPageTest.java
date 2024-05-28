package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Step;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
	//	registrationPage = loginPage.navigateToRegisterPage();
		registrationPage = loginPage.navigateToMenuRegisterPage();
	}
	
	@Test
	public void getRegistrationPageTitleTest() {
		Assert.assertEquals(registrationPage.getRegPageTitle(), AppConstants.REGISTRATION_PAGE_TITLE);
	}
	
	@Test
	public void getRegistrationPageURLTest() {
		String actualURL = registrationPage.getRegPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.REGISTRATION_PAGE_URL_FRACTION));
	}
	
	@DataProvider
	public Object[][] getuserRegTestData() {
		return new Object[][] {
			{"Sheema", "Farheen", "158745821", "youyou@123", "yes"},
			{"Armaan", "Hey", "158757821", "youyou@123", "no"},
			{"Maya", "Naya", "958745821", "youyou@023", "yes"},
		};
	}
	
	@DataProvider
	public Object[][] getuserRegTestDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@DataProvider (name="csvregdata")    //DataProvider name
	public Object[][] getuserRegTestDataFromCsv() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Step("Checking User Registration...")
	@Test(dataProvider="getuserRegTestDataFromCsv")   //can use method name or DataProvider name
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registrationPage.userRegister(firstName, lastName, StringUtils.getRandomEmailUUId(), telephone, password, subscribe));
		
	}
}
