package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	//Page class/ Page Library/ Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. Private By Locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWDLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	private By currencyDropdownMenu = By.xpath("//form[@id='form-currency']");
	private By euroCurrencyButton = By.xpath("//button[text()='€ Euro']");
	private By poundCurrencyButton = By.xpath("//button[text()='£ Pound Sterling']");
	private By usDollarCurrencyButton = By.xpath("//button[text()='$ US Dollar']");
	
	private By contactMenu = By.xpath("(//div[@id='top-links']/ul/li)[1]");
	private By myAccountDropdownMenu = By.xpath("(//div[@id='top-links']/ul/li)[2]");
	private By registerDropDown = By.xpath("//li[@class='dropdown open']//a[text()='Register']");
	private By wishListMenu = By.xpath("(//div[@id='top-links']/ul/li)[3]");
	private By shoppingCartMenu = By.xpath("(//div[@id='top-links']/ul/li)[4]");
	private By checkoutMenu = By.xpath("(//div[@id='top-links']/ul/li)[5]");
	
	
	//2. Public Page Class Constructors
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	
	//3. Public Page Actions/Method
	@Step("Getting login page title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		
//		System.out.println("Login Page title : "+title);
		Log.info("Login Page title : "+title);
		return title;
	}
	
	@Step("Getting login page URL...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
//		System.out.println("Login Page url : "+url);
		Log.info("Login Page url : "+url);
		return url;
	}
	
	@Step("Getting the state of forgot pwd link...")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPWDLink);
	}
	
	@Step("Login with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		Log.info("user creds: "+username+" : "+pwd);
		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_LONG_TIME).sendKeys(username);;
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		return new AccountsPage(driver);    //PageChainingModel/ZigZag Pattern - Method returning the next Landing Page Class Object
	}
	
	@Step("Navigating to registration page...")
	public RegistrationPage navigateToRegisterPage() {
		eleUtil.waitForElementVisible(registerLink, TimeUtil.DEFAULT_LONG_TIME).click();;
		return new RegistrationPage(driver);   //returns the RegistrationPage Class Object
	}
	
	//goto Register page via MyAccount top Menu - register dropdown
	public RegistrationPage navigateToMenuRegisterPage() {
		eleUtil.waitForElementVisible(myAccountDropdownMenu, TimeUtil.DEFAULT_LONG_TIME).click();
		eleUtil.waitForElementVisible(registerDropDown, TimeUtil.DEFAULT_LONG_TIME).click();
		return new RegistrationPage(driver);   //returns the RegistrationPage Class Object
	}
	
	
}
