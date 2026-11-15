package com.amazon.customelisteners;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.amazon.basetest.BaseTest;
import com.amazon.reports.ExtentReportManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class MyListener extends BaseTest implements ITestListener, IAnnotationTransformer {

	@Override
	public void onTestStart(ITestResult result) {
		// Use this method to perform some action when Test is started

		String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf('.') + 1);
		String methodName = result.getMethod().getMethodName();
		logger.info("Started Test: " + className +" => " +methodName);
		
		String  xmlTest = result.getTestContext().getName();
		String groupsList[] = result.getTestContext().getIncludedGroups();
		String includedGroupsList = String.join(",", groupsList);
		ExtentReportManager.createTest(className+"."+methodName,includedGroupsList,className,xmlTest,"John");
		ExtentReportManager.getTest().log(Status.INFO, "Test Started: " + className + " => " + methodName);
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {

		String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf('.') + 1);
		String methodName = result.getMethod().getMethodName();
		
		ExtentReportManager.getTest().log(Status.PASS, "Test Successful: " + className + " => " + methodName);
		logger.info("Test Successful: " + className + " => " + methodName);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf('.') + 1);
		String methodName = result.getMethod().getMethodName();
		logger.info("Test Failed: " + className +" => " +methodName);
		
		ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + className + " => " + methodName);
		
		ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());

		
		//System.out.println("***********Test Failed************** Test Name ==> " + result.getName());
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

		String dynamicScreenshotName = result.getName() + formatter.format(now);

		File fIn = ((TakesScreenshot) getLocalDriver()).getScreenshotAs(OutputType.FILE);
		try {
			String screenshotPath = System.getProperty("user.dir") + "/src/test/resources/Screenshots/"
					+ dynamicScreenshotName+".png";
			File destinationFile = new File(screenshotPath);
			
			FileUtils.copyFile(fIn, destinationFile);
			
			//Add ss in Extent Report
			ExtentReportManager.getTest().fail("Screenshot for Failure: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

			
		} catch (IOException io) {
			io.printStackTrace();
			ExtentReportManager.getTest().fail("Failed to capture screenshot: " + io.getMessage());
			logger.error(io.getMessage());
		}

	}
	
	@Override
	public void onTestSkipped(ITestResult result) {

		String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf('.') + 1);
		String methodName = result.getMethod().getMethodName();
		
		ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + className + " => " + methodName);
		ExtentReportManager.getTest().log(Status.SKIP, result.getThrowable());
		logger.info("Test Skipped: " + className + " => " + methodName);
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(MyRetryAnalyzer.class);
		
	}
}
