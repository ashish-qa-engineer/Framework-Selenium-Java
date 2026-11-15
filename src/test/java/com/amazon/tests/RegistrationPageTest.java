package com.amazon.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amazon.basetest.BaseTest;

public class RegistrationPageTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void setupPage() {
		// Open the Registration page from Home page of Amazon
		homePage.openRegistrationPage();
		signInPage.setupCorrectRegistrationPage();
	}

	@Test(groups = { "sanity" })
	public void isLogoPresentTest() {
		try {
			Assert.assertTrue(registrationPage.verifyLogo(), "ERROR - Logo is not present on the page");
		} catch (AssertionError ae) {
			logger.error(ae.getMessage());
			throw ae;
		}

	}

	@Test(groups = { "sanity" })
	public void pageTitleTest() {
		try {
			String actualTitle = registrationPage.getTitleOfThePage();
			String expectedTitle = "Amazon Registration";
			Assert.assertEquals(actualTitle, expectedTitle, "ERROR - Page title does not match.");
			logger.info("Page title test passed: " + actualTitle);
		} catch (AssertionError e) {
			logger.error(e.getMessage());
			throw e; // Rethrow the exception to ensure the test fails
		}
	}

	@Test(groups = { "sanity", "regression" })
	public void verifyMobileNumberTest() {
		registrationPage.verifyMobileNumberFunctionality("Testing yourname", "0000000000", "abcd1234");

		// Asseet user redirect to Authentication page
		// We can multiple assertions
		Assert.assertEquals(registrationPage.getTitleOfThePage(), "Authentication required");

	}

	@Test(groups = { "sanity", "regression" })
	public void signInLinkTest() {
		registrationPage.clickOnSignInLink();
		Assert.assertEquals(signInPage.getTitleOfThePage(), "Amazon Sign In",
				"ERROR - SignIn page not opened after clicking on SignIn link");
		Assert.assertTrue(signInPage.getSignInText().isDisplayed(),
				"ERROR - SignIn page not opened after clicking on SignIn link");
	}

	@Test(groups = { "sanity", "regression" })
	public void createBusinessAccountTest() {
		registrationPage.clickOnCreateBusinessAccountLink();
		// Assert title of the page
		Assert.assertEquals(businessPage.getTitleOfThePage(), "Amazon Business",
				"ERROR - Amazon Business page not opened after clicking on Create Business Account link");

	}

	@Test(groups = { "sanity" })
	public void fieldsOnThePageTest() throws IOException {

		List<String> fieldNames = excelReader.getFieldNamesFromExcel("RegistrationPage");
		Assert.assertTrue(registrationPage.verifyPresenceOfElementsOnPage(fieldNames),
				"Some elements are not present on the registration page.");

		// We can store all the expected fields in excel and compare actual fields at
		// run time
		// Assert all the fields and print if any field is not present from the expected
		// list

	}
}
