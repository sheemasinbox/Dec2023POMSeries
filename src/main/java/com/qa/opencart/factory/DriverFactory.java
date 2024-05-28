package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	/*ThreadLocal is a JavaClass responsible for Safe Thread Management.
	Not mandatory but important. Helps to run TCs in parallel mode and proper reporting.
	Initialize driver with ThreadLocal, so every thread will get its own copy of the driver.
	Has 2 methods - get() - returns ThreadLocal driver and set() - set driver with ThreadLocal.ss*/

	public static String highlight;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

//		System.out.println("browser name is: " + browserName);
		Log.info("browser name is: " + browserName);
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver(optionsManager.chromeOptions());
			tlDriver.set(driver);
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.firefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.firefoxOptions()));
			break;
		case "egde":
			driver = new EdgeDriver(optionsManager.edgeOptions());
			tlDriver.set(driver);
			break;
		case "safari":
			driver = new SafariDriver();
			tlDriver.set(driver);
			break;

		default:
//			System.out.println("plz pass the right browser.." + browserName);
			Log.error("plz pass the right browser.." + browserName);
			throw new BrowserException("Browser not found.. " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		prop = new Properties();

		// Env - qa/ dev/stage/ uat/ prod
		// Supply env name from Maven Command Line:
		// mvn clean install -Denv = "qa" (-D is used for command line env variable)(env
		// can be any name for environment)

		String envname = System.getProperty("env"); // to read this variable in java --- "env" from line#66
		FileInputStream ip = null;

		try {
			if (envname == null) {
				System.out.println("Since env is null, Runnning the tests in QA environment by default");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties"); // default env set to "QA"
			} else {

				switch (envname.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.prod.properties");
					break;

				default:
					System.out.println("Please pass the correct environment name");
					throw new FrameworkException(AppError.ENV_NOT_FOUND + " : " + envname);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}
	
	// Properties class - responsible for reading data from the properties
	// FileInputStream class - will establish a connection with config.properties
	// FileInputStream object takes the path of the config.properties
	// rt-click config.properties file and copy the src path. give ./ and the path.
	// ./ means in the current project directory
	// prop.load(ip) - loads all the properties from the stream to this 'prop'
	// object.

	/**
	 * take screenshot
	 * Convert the getDriver() i.e, tlDriver into TakesScreenshot Interface & access getScreenShotAs method
	 * "user.dir" - Project Folder -> screenshot folder
	 * As File cannot be store in String path. Covert String to File.
	 * Use FileHandler class - copy()
	 * @return path at which screenshot file is saved
	 */
	public static String getScreenShot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE); //temp dir
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		
		File destination = new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
