package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class RegistrationPage {

	//Page class/ Page Library/ Page Object
			private WebDriver driver;
			private ElementUtil eleUtil;
			
			//1. Private By Locators
			
			private By logoutLink = By.linkText("Logout");
			private By registerLink = By.linkText("Register");
			private By firstName = By.id("input-firstname");
			private By lastName = By.id("input-lastname");
			private By email = By.xpath("//input[@placeholder='E-Mail']");
			private By telephone = By.cssSelector("input[name='telephone']");
			private By password = By.id("input-password");
			private By confirmPassword = By.id("input-confirm");
			private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@name='newsletter' and @value='1']");
			private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@name='newsletter' and @value='0']");
			private By agreeCheckbox = By.xpath("//input[@type='checkbox' and @name='agree']");
			private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
			private By successMsg = By.cssSelector("div[id='content'] h1");
			
			//2. Public Page Class Constructors
			public RegistrationPage(WebDriver driver) {
				this.driver = driver;
				eleUtil = new ElementUtil(driver);
			}
		
			//3. Public Page Actions/Method
			public String getRegPageTitle() {
				String regTitle = eleUtil.waitForTitleIs(AppConstants.REGISTRATION_PAGE_TITLE, 5);
				System.out.println("The Registration Page Title is : "+regTitle);
				return regTitle;
			}
			
			public String getRegPageURL() {
				String regURL = eleUtil.waitForUrlContains(AppConstants.REGISTRATION_PAGE_URL_FRACTION, 5);
				System.out.println("The Registration Page URL is : "+regURL);
				return regURL;
			}
			
			@Step("User registration")
			public boolean userRegister(String firstName, String lastName, String email, 
					String telephone, String password, String subscribe) {
				eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);
				eleUtil.doSendKeys(this.lastName, lastName);
				eleUtil.doSendKeys(this.email, email);
				eleUtil.doSendKeys(this.telephone, telephone);
				eleUtil.doSendKeys(this.password, password);
				eleUtil.doSendKeys(this.confirmPassword, password);
				
				if(subscribe.equalsIgnoreCase("yes")) {
					eleUtil.doClick(subscribeYes);
				}
				else
					eleUtil.doClick(subscribeNo);
				
				eleUtil.doClick(agreeCheckbox);
				eleUtil.doClick(continueBtn);
				
				String regSuccessMsg = eleUtil.waitForElementVisible(successMsg, 10).getText();
				System.out.println(regSuccessMsg);
				if(regSuccessMsg.equals(AppConstants.USER_REG_SUCCESS_MSG)) {
					eleUtil.doClick(logoutLink);
					eleUtil.doClick(registerLink);
					return true;
				}
				else return false;
			}
}
