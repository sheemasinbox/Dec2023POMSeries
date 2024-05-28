package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	// Page class/ Page Library/ Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By searchProducts = By.cssSelector("div.product-thumb");

	// 2. Public Page Class Constructors
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Method
	public int getSearchProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("searching for product : "+productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		return new ProductInfoPage(driver);
	}

}
