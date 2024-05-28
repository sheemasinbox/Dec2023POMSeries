package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	//Page class/ Page Library/ Page Object
			private WebDriver driver;
			private ElementUtil eleUtil;
			
			private Map<String, String> productMap = new HashMap<String, String>();
//			private Map<String, String> productMap = new LinkedHashMap<String, String>(); //maintains insertion order
//			private Map<String, String> productMap = new TreeMap<String, String>();       //maintains sorted order
			
			
			//1. Private By Locators
			
			private By productHeader = By.tagName("h1");
			private By images = By.cssSelector("ul.thumbnails img");
			private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
			private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
			private By quantity = By.id("input-quantity");
			private By addToCartButton = By.id("button-cart");
			private By successMsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");
			private By successMsgShoppingCart = By.xpath("//div[@class='alert alert-success alert-dismissible']/a[text()='shopping cart']");
			private By shoppingCartMenu = By.xpath("(//div[@id='top-links']/ul/li)[4]");
			
			//2. Public Page Class Constructors
			public ProductInfoPage(WebDriver driver) {
				this.driver = driver;
				eleUtil = new ElementUtil(driver);
			}
		
			//3. Public Page Actions/Method
			public String getProductHeader() {
				String header= eleUtil.doGetElementText(productHeader);
				System.out.println(header);
				return header;
			}
			
			public int getProductImagesCount() {
				int totalImages = eleUtil.waitForElementsVisible(images, 10).size();
				System.out.println("Image count for "+getProductHeader()+" : "+totalImages);
				return totalImages;
			}
			
//			Brand: Apple
//			Product Code: Product 18
//			Reward Points: 800
//			Availability: In Stock
			private void getProductMetaData() {
				List<WebElement> metaList = eleUtil.getElements(productMetaData);
				for (WebElement e: metaList) {
					String text = e.getText();
					String metaKey = text.split(":")[0].trim();
					String metaValue = text.split(":")[1].trim();
					productMap.put(metaKey, metaValue);
				}	
			}
			
//			$2,000.00
//			Ex Tax: $2,000.00
			private void getProductPriceData() {
				List<WebElement> priceList = eleUtil.getElements(productPriceData);
					
				String price = priceList.get(0).getText();
				String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
				productMap.put("productprice", price);
				productMap.put("extaxprice", exTaxPrice);	
			}
			
			public Map<String, String> getProductDetailsMap() {
				productMap.put("header", getProductHeader());
				productMap.put("productimages", String.valueOf(getProductImagesCount()));    //converting int to string
				getProductMetaData();
				getProductPriceData();
				System.out.println(productMap);
				return productMap;	
			}
			
			public String doAddToCart() {
				eleUtil.doSendKeys(quantity, "3", 5);
				System.out.println(eleUtil.getElement(quantity).getAttribute("value"));
				eleUtil.doClick(addToCartButton, 5);
				String successText = eleUtil.doGetElementText(successMsg, 5);
				System.out.println(successText);
				return successText;
			}
			
			public ShoppingCartPage goToShoppingCart() {
//				eleUtil.doClick(shoppingCartMenu);
				if(doAddToCart().contains("Success")) {
					eleUtil.doClick(successMsgShoppingCart);
				}
				return new ShoppingCartPage(driver);    //returns the ShoppingCartPage class object
			}
}
