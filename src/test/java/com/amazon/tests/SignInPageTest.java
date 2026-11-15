package com.amazon.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amazon.basetest.BaseTest;

public class SignInPageTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void setupPage() {
		// Open the signIn page from Home page of Amazon
		homePage.openSignInPage();
		signInPage.setupCorrectSignInPage();

	}

	@Test(groups = { "sanity" })
	public void isLogoPresentTest() {
		try {
			Assert.assertTrue(signInPage.verifyLogo(), "ERROR - Logo not present");
		} catch (AssertionError ae) {
			logger.error(ae.getMessage());
			throw ae;
		}
	}

	// Verify_Page_Title

	@Test(groups = { "sanity" })
	public void pageTitleTest() {
		try {
			String actualTitle = signInPage.getTitleOfThePage();
			String expectedTitle = "Amazon Sign In";
			Assert.assertEquals(actualTitle, expectedTitle, "ERROR - Page title does not match.");
			logger.info("Page title test passed: " + actualTitle);
		} catch (AssertionError e) {
			logger.error(e.getMessage());
			throw e; // Rethrow the exception to ensure the test fails
		}
	}

	/*
	 * Verify_Back-And-Forth_Scenarios Navigate back and verify previous page
	 * Navigate forward and verify sign page refresh the page and verify if we
	 * navigate to same page
	 */

	@Test(groups = { "regression" })
	public void backAndForthScenariosTest() {

		basePage.navigateBack();
		Assert.assertEquals(homePage.getTitleOfThePage(),
				"Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
		basePage.navigateForward();
		Assert.assertEquals(signInPage.getTitleOfThePage(), "Amazon Sign In");
		basePage.refreshPage();
		Assert.assertEquals(signInPage.getTitleOfThePage(), "Amazon Sign In");

	}

	// Sign_In_With_Unregistered_Email_Id

	@Test(groups = { "regression" })
	public void signInWithUnregisteredEmailIdTest() {

		signInPage.enterEmailId("abcdsdfdsfdf@testffasfas.com");
		signInPage.clickOnContinueButton();

		// Assert error
		Assert.assertEquals(signInPage.readUnregisteredEmailIdError(),
				"We cannot find an account with that email address");
	}

	// Sign_In_With_Invalid_Email_Id

	@Test(groups = { "sanity1","regression" })
	public void signInWithInvalidEmailIdTest() {

		signInPage.enterEmailId("abcdinvalidesdasmail@testingcmp.com");
		signInPage.clickOnContinueButton();

		// Assert error
		Assert.assertEquals(signInPage.readInvalidEmailIdError(),
				"Wrong or Invalid email address or mobile phone number. Please correct and try again.");
	}

	// sign_with_Valid_Email_Id_And_Pwd

	@Test(groups = { "regression"})
	public void signInWithValidCredentialsTest() {

		signInPage.signInWithValidCredentials("asdas", "asdasdfsfd");
		// Assert sign in successful by checking title of page or any element on page
		// Multiple assertions

	}

	// Verify input fields/ field labels / links / buttons on sign in page

	@Test(groups = { "sanity", "regression" })
	public void fieldsOnThePageTest() throws IOException {
		List<String> fieldNames = excelReader.getFieldNamesFromExcel("SignInPage");
		Assert.assertTrue(signInPage.verifyPresenceOfElementsOnPage(fieldNames),
				"Some elements are not present on the sign in page.");

		// We can store all the expected fields in excel and compare actual fields at
		// run time
		// Assert all the fields and print if any field is not present from the expected
		// list

	}

}

