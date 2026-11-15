package com.amazon.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.*;

import com.amazon.utils.ConfigReader;
import com.amazon.utils.CustomWait;

public class BasePage {

	private WebDriver driver;
	protected CustomWait wait;
	protected ConfigReader configReader;
	protected static final Logger logger = LogManager.getLogger(BasePage.class);

	@FindBy(xpath = "//i[@class='a-icon a-icon-logo' and @aria-label='Amazon']")
	private WebElement logo;

	// Constructor

	public BasePage(WebDriver driver) {
		this.driver = driver;
		configReader = new ConfigReader();
		PageFactory.initElements(driver, this);
		this.wait = new CustomWait(driver, Duration.ofSeconds(configReader.getGlobalWaitValue()));
	}

	public WebDriver getDriver(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");

			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();

		} else {
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		}

		return driver;

	}

	// Getter Methods //
	public WebElement getLogo() {
		wait.waitForVisibilityOfElement(logo);
		return logo;
	}

	public void quitDriver() {
		driver.quit();
	}

	// Common Methods //

	// Verify Logo
	public boolean verifyLogo() {
		return getLogo().isDisplayed();
	}

	// Verify WebPage Title
	public String getTitleOfThePage() {
		return driver.getTitle();
	}

	// Current WebPage URL
	public String getCurrentPageUrl() {
		return driver.getCurrentUrl();
	}

	// Navigate Back
	public void navigateBack() {
		driver.navigate().back();
	}

	// Navigate Forward
	public void navigateForward() {
		driver.navigate().forward();
	}

	// Refresh the Current WebPage
	public void refreshPage() {
		driver.navigate().refresh();
	}

	public WebDriver getRemoteDriver(String browser) throws MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (browser.equalsIgnoreCase("chrome")) {
			capabilities.setBrowserName(configReader.getBrowser());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			capabilities.merge(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			capabilities.setBrowserName(configReader.getBrowser());
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-private");
			capabilities.merge(options);
		} else {
			capabilities.setBrowserName(configReader.getBrowser());
			EdgeOptions options = new EdgeOptions();
			options.addArguments("-inprivate");
			capabilities.merge(options);
		}
		String gridUrl = configReader.getUrl(); // Fetch this from config.properties
		driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
		driver.manage().window().maximize();
		return driver;
	}

}
