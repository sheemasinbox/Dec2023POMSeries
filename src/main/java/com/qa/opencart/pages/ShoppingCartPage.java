package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ShoppingCartPage {

	//Page class/ Page Library/ Page Object
		private WebDriver driver;
		private ElementUtil eleUtil;
		
		//1. Private By Locators
		private By emailId = By.id("input-email");
		
		
		//2. Public Page Class Constructors
		public ShoppingCartPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
		}
		
		//3. Public Page Actions/Method
		public String getShoppingCartTitle() {
			String title = eleUtil.waitForTitleIs(AppConstants.SHOPPINGCART_PAGE_TITLE, 5);
			System.out.println("ShoppingCart Page title : "+title);
			return title;
		}
		
		public String getShoppingCartURL() {
			String url = eleUtil.waitForUrlContains(AppConstants.SHOPPINGCART_PAGE_URL_FRACTION, 5);
			System.out.println("ShoppingCart Page url : "+url);
			return url;
		}
}
