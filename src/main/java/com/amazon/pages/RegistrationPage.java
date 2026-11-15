package com.amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.base.BasePage;

public class RegistrationPage extends BasePage {

	// WebElements on Registration Page

	@FindBy(id = "ap_customer_name")
	private WebElement yourName;

	@FindBy(id = "ap_phone_number")
	private WebElement mobileNumber;

	@FindBy(id = "ap_password")
	private WebElement password;

	@FindBy(id = "ab-enhanced-registration-link")
	private WebElement createBusinessAccount;

	@FindBy(xpath = "//div[contains(text(),'Passwords must be at least 6 characters.')]")
	private WebElement passwordInfoMessage;

	@FindBy(xpath = "//span[@id='auth-continue']")
	private WebElement verifyMobileNumberButton;

	@FindBy(id = "mobileClaimDisclosureMessageRelaxed")
	private WebElement verifyMobileNumberMessage;

	@FindBy(xpath = "//*[contains(text(),'Already have an account?')]")
	private WebElement alreadyHaveAccountText;

	@FindBy(xpath = "//a[contains(text(),'Sign in instead') or contains(text(),'Sign in')]")
	private WebElement signInLink;

	public RegistrationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// GETTER Methods for WebElements //

	WebElement getYourName() {
		wait.waitForVisibilityOfElement(yourName);
		return yourName;
	}

	public WebElement getMobileNumber() {
		wait.waitForVisibilityOfElement(mobileNumber);
		return mobileNumber;
	}

	public WebElement getPassword() {
		wait.waitForVisibilityOfElement(password);
		return password;
	}

	public WebElement getCreateBusinessAccount() {
		wait.waitForVisibilityOfElement(createBusinessAccount);
		return createBusinessAccount;
	}

	public WebElement getPasswordInfoMessage() {
		wait.waitForVisibilityOfElement(passwordInfoMessage);
		return passwordInfoMessage;
	}

	public WebElement getVerifyMobileNumberButton() {
		wait.waitForElementToBeClickable(verifyMobileNumberButton);
		return verifyMobileNumberButton;
	}

	public WebElement getVerifyMobileNumberMessage() {
		wait.waitForVisibilityOfElement(verifyMobileNumberMessage);
		return verifyMobileNumberMessage;
	}

	public WebElement getAlreadyHaveAccountText() {
		wait.waitForVisibilityOfElement(alreadyHaveAccountText);
		return alreadyHaveAccountText;
	}

	public WebElement getSignInLink() {
		wait.waitForVisibilityOfElement(signInLink);
		return signInLink;
	}

	// PAGE ACTIONS //

	public void enterYourName(String name) {
		try {
			getYourName().sendKeys(name);
			logger.info("Entered name: " + name);
		} catch (Exception e) {
			logger.error("Failed to enter name in yuorName field: " + e);
			throw e;
		}

	}

	public void enterMobileNumber(String mNumber) {
		try {
			getMobileNumber().sendKeys(mNumber);
			logger.info("Entered mobile number: " + mNumber);
		} catch (Exception e) {
			logger.error("Failed to enter mobile number: " + mNumber, e);
			throw e;
		}
	}

	public void enterPassword(String pwd) {
		getPassword().sendKeys(pwd);

	}

	public void clickOnVerifyMobileNumberButton() {
		getVerifyMobileNumberButton().click();
	}

	public void verifyMobileNumberFunctionality(String name, String number, String pwd) {
		enterYourName(name);
		enterMobileNumber(number);
		enterPassword(pwd);
		clickOnVerifyMobileNumberButton();
	}

	public void clickOnSignInLink() {
		getSignInLink().click();
	}

	public void clickOnCreateBusinessAccountLink() {
		getCreateBusinessAccount().click();

	}

	public boolean verifyPresenceOfElementsOnPage(List<String> fieldNames) {
		boolean result = true;
		int count = 0;
		for (String fieldName : fieldNames) {

			WebElement element = getElementByFieldName(fieldName);

			if (element == null) {
				System.out.println(
						"Element is null, so please make sure you have added corresponding WebElement case for this field "
								+ fieldName + " in excel");
				result = false;
				continue;
			}

			if (wait.getElementPresentState() == true) {
				if (element.isDisplayed() == true) {
					System.out.println(fieldName + " is present on the page.");
				}
			} else {
				System.out.println(fieldName + " is not present on the page.");
				count++;
			}
		}
		if (count != 0) {
			result = false;
		}
		return result;
	}

	private WebElement getElementByFieldName(String fieldName) {
		WebElement element = null;
		try {
			switch (fieldName.toLowerCase()) {

			case "your name":
				getYourName();
				element = yourName;
				break;

			case "mobile number":
				getMobileNumber();
				element = mobileNumber;
				break;

			case "password":
				getPassword();
				element = password;
				break;

			case "verify mobile number button":
				getVerifyMobileNumberButton();
				element = verifyMobileNumberButton;
				break;

			case "create business account":
				getCreateBusinessAccount();
				element = createBusinessAccount;
				break;

			case "sign in link":
				getSignInLink();
				element = password;
				break;

			case "already have an account":
				getAlreadyHaveAccountText();
				element = password;
				break;
			default:
				throw new IllegalArgumentException("Invalid field name: " + fieldName);
			}
		} catch (IllegalArgumentException ie) {
			System.out.println(ie.getMessage());

		}
		return element;

	}

}
