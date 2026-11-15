package com.amazon.basetest;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.amazon.base.BasePage;
import com.amazon.pages.BusinessPage;
import com.amazon.pages.HomePage;
import com.amazon.pages.RegistrationPage;
import com.amazon.pages.SignInPage;
import com.amazon.reports.ExtentReportManager;
import com.amazon.testdatareaders.ExcelReader;
import com.amazon.utils.ConfigReader;
import com.aventstack.extentreports.Status;

public class BaseTest {
	private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();
	protected static final Logger logger = LogManager.getLogger(BaseTest.class);

	public BasePage basePage;
	public WebDriver driver;
	public RegistrationPage registrationPage;
	public SignInPage signInPage;
	public HomePage homePage;
	public BusinessPage businessPage;
	public ConfigReader configReader;
	public ExcelReader excelReader;

	private static int totalTests = 0;
	private static int passedTests = 0;
	private static int failedTests = 0;
	private static int skippedTests = 0;

	public WebDriver getLocalDriver() {
		return localDriver.get();
	}

	@BeforeSuite(alwaysRun = true)
	public void setupSuite(ITestContext context) {
		configReader = new ConfigReader();
		ExtentReportManager.setupExtentReport();
		if (configReader.getParallelMode().equalsIgnoreCase("Yes")) {
			context.getSuite().getXmlSuite().setThreadCount(Integer.parseInt(configReader.getMaxParallelTests()));
		}
		String runMode = configReader.getRunMode();
		String suiteName = context.getSuite().getName();
		String groupsList[] = context.getIncludedGroups();
		String includedGroupsList = String.join(",", groupsList);
		logger.info("Starting Execution for Test Suite=> " + suiteName + " with Tag=> " + includedGroupsList);
		ExtentReportManager.getReport().setSystemInfo("Suite Name", suiteName);
		ExtentReportManager.getReport().setSystemInfo("Run Mode", runMode);

	}

	@BeforeTest(alwaysRun = true)
	public void setupTest(ITestContext context) {
		String testName = context.getName();
		logger.info("Starting Execution for Test=> " + testName);
		ExtentReportManager.getReport().setSystemInfo("Test Name", testName);
	}

	@BeforeMethod(alwaysRun = true)
	public void baseSetup() throws MalformedURLException {
		configReader = new ConfigReader();
		WebDriver driver;
		
		if (configReader.getRunMode().equalsIgnoreCase("Docker")) {
			// Set up RemoteWebDriver for Docker Selenium Grid
			basePage = new BasePage(getLocalDriver());
			driver = basePage.getRemoteDriver(configReader.getBrowser());
		} else {
			// Use local WebDriver
			basePage = new BasePage(getLocalDriver());
			driver = basePage.getDriver(configReader.getBrowser());
		}

		logger.info("***Opening Browser - " + configReader.getBrowser());
		localDriver.set(driver);
		getLocalDriver().get(configReader.getUrl());

		// Print the session ID , ThreadID
		long threadID = Thread.currentThread().getId();
		String sessionId = ((RemoteWebDriver) getLocalDriver()).getSessionId().toString();
		System.out.println("Thread ID: " + threadID + "- Starting Session ID: " + sessionId);

		registrationPage = new RegistrationPage(getLocalDriver());
		signInPage = new SignInPage(getLocalDriver());
		homePage = new HomePage(getLocalDriver());
		businessPage = new BusinessPage(getLocalDriver());

		// Excel Reader
		excelReader = new ExcelReader(
				"./src/test/resources/test-data/" + configReader.getFieldsVerificationExcelName());

	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser(ITestResult result) {

		totalTests++;

		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReportManager.getTest().fail(result.getThrowable());
			failedTests++;
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentReportManager.getTest().skip(result.getThrowable());
			skippedTests++;
		} else {
			ExtentReportManager.getTest().pass("Test passed");
			passedTests++;
		}

		ExtentReportManager.getTest().log(Status.INFO, "Test Method Execution completed");

		getLocalDriver().quit();
		localDriver.remove();
		logger.info("Closing Browser - " + configReader.getBrowser());

	}

	@AfterSuite(alwaysRun = true)
	public void generateReport() {

//		ExtentReportManager.getReport().setSystemInfo("Total Tests Executed", String.valueOf(totalTests-skippedTests));
//		ExtentReportManager.getReport().setSystemInfo("Passed Tests", String.valueOf(passedTests));
//		ExtentReportManager.getReport().setSystemInfo("Failed Tests", String.valueOf(failedTests));
//		ExtentReportManager.getReport().setSystemInfo("Skipped Tests", String.valueOf(skippedTests));

		double passPercentage = (double) passedTests / (totalTests - skippedTests);
		passPercentage = passPercentage * 100;

		// ExtentReportManager.getReport().setSystemInfo("Passed %",
		// String.format("%.2f",passPercentage));

		ExtentReportManager.createCustomTable(

				String.valueOf(totalTests - skippedTests), String.valueOf(passedTests), String.valueOf(failedTests),
				String.valueOf(skippedTests), String.format("%.2f", passPercentage), configReader.getBrowser(),
				configReader.getUrl()

		);
		// Generate and add the custom table before flushing the report

		ExtentReportManager.flushReport();

	}

}
