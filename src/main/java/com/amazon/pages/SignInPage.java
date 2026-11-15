package com.amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.base.BasePage;

public class SignInPage extends BasePage {

	// WebElements on Sign In Page

	@FindBy(xpath = "//h1[contains(text(),'Sign in')]")
	private WebElement signInText;

	@FindBy(xpath = "//*[contains(text(),'Email or mobile phone number')]")
	private WebElement EmailormobilephonenumberText;

	@FindBy(xpath = "//*[@id='ap_email_login' or @id='ap_email']")
	private WebElement emailInputBox;

	@FindBy(xpath = "//input[@id='continue' or @type='submit']")
	private WebElement continueButton;

	@FindBy(id = "legalTextRow")
	private WebElement conditionsAndPrivacyMesage;

	@FindBy(className = "a-expander-prompt")
	private WebElement needHelp;

	@FindBy(id = "createAccountSubmit")
	private WebElement createAccountButton;

	@FindBy(id = "ap_password")
	private WebElement passwordInputBox;

	@FindBy(id = "signInSubmit")
	private WebElement signInButton;

	@FindBy(id = "auth-fpp-link-bottom")
	private WebElement forgotPasswordLink;

	@FindBy(xpath = "//span[@class='a-list-item' and contains(text(),'We cannot find an account with that email address')]")
	private WebElement unregisteredEmail;

	@FindBy(xpath = "//div[@class='a-alert-content' and contains(text(),'Wrong or Invalid email address or mobile phone number. Please correct and try again.')]")
	private WebElement invalidEmail;

	@FindBy(xpath = "//h1[contains(text(),'Sign in or create account')]")
	private WebElement signInOrCreateAccount;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement proceedToCreateAccount;

	public SignInPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	// GETTER Methods for WebElements //

	public WebElement getSignInText() {
		wait.waitForVisibilityOfElement(signInText);
		return signInText;
	}

	public WebElement getEmailOrMobilePhoneNumberText() {
		wait.waitForVisibilityOfElement(EmailormobilephonenumberText);
		return EmailormobilephonenumberText;
	}

	public WebElement getEmailInputBox() {
		wait.waitForVisibilityOfElement(emailInputBox);
		return emailInputBox;
	}

	public WebElement getContinueButton() {
		wait.waitForVisibilityOfElement(continueButton);
		return continueButton;
	}

	public WebElement getConditionsAndPrivacyMesage() {
		wait.waitForVisibilityOfElement(conditionsAndPrivacyMesage);
		return conditionsAndPrivacyMesage;
	}

	public WebElement getNeedHelp() {
		wait.waitForVisibilityOfElement(needHelp);
		return needHelp;
	}

	public WebElement getCreateAccountButton() {
		wait.waitForVisibilityOfElement(createAccountButton);
		return createAccountButton;
	}

	public WebElement getPasswordInputBox() {
		wait.waitForVisibilityOfElement(passwordInputBox);
		return passwordInputBox;
	}

	public WebElement getSignInButton() {
		wait.waitForVisibilityOfElement(signInButton);
		return signInButton;
	}

	public WebElement getForgotPasswordLink() {
		wait.waitForVisibilityOfElement(forgotPasswordLink);
		return forgotPasswordLink;
	}

	public WebElement getUnregisteredEmailError() {
		wait.waitForVisibilityOfElement(unregisteredEmail);
		return unregisteredEmail;
	}

	public WebElement getInvalidEmailError() {
		wait.waitForVisibilityOfElement(invalidEmail);
		return invalidEmail;
	}

	public WebElement getSignOrCreateAccountHeader() {
		wait.waitForVisibilityOfElement(signInOrCreateAccount);
		return signInOrCreateAccount;
	}

	public WebElement getProceedToCreateAccountButton() {
		wait.waitForElementToBeClickable(proceedToCreateAccount);
		return proceedToCreateAccount;
	}

	// PAGE ACTIONS //

	public void enterEmailId(String email) {
		try {
			getEmailInputBox().sendKeys(email);
			logger.info("Entering Email ID: " + email);
		} catch (Exception e) {
			logger.error("Failed to enter Email ID: " + email, e);
			throw e;
		}
	}

	public void clickOnContinueButton() {
		try {
			getContinueButton().click();
			logger.info("Clicking on Continue button.");
		} catch (Exception e) {
			logger.error("Failed to click on Continue button.", e);
			throw e;
		}
	}

	public String readUnregisteredEmailIdError() {
		try {
			String error = getUnregisteredEmailError().getText();
			logger.info("Read unregistered Email ID error: " + error);
			return error;
		} catch (Exception e) {
			logger.error("Failed to read unregistered Email ID error.", e);
			throw e;
		}
	}

	public String readInvalidEmailIdError() {
		try {
			String error = getInvalidEmailError().getText();
			logger.info("Read invalid Email ID error: " + error);
			return error;
		} catch (Exception e) {
			logger.error("Failed to read invalid Email ID error.", e);
			throw e;
		}
	}

	public void enterPassword(String pwd) {
		try {
			getPasswordInputBox().sendKeys(pwd);
			logger.info("Entering password.");
		} catch (Exception e) {
			logger.error("Failed to enter password.", e);
			throw e;
		}
	}

	public void clickOnSignInButton() {
		try {
			getSignInButton().click();
			logger.info("Clicking on Sign In button.");
		} catch (Exception e) {
			logger.error("Failed to click on Sign In button.", e);
			throw e;
		}
	}

	public void signInWithValidCredentials(String validEmail, String validPwd) {
		try {
			enterEmailId(validEmail);
			clickOnContinueButton();
			enterPassword(validPwd);
			clickOnSignInButton();
			logger.info("Signed in with valid credentials: " + validEmail);
		} catch (Exception e) {
			logger.error("Failed to sign in with valid credentials: " + validEmail, e);
			throw e;
		}
	}

	public void setupCorrectRegistrationPage() {
		try {
			wait.waitForVisibilityOfElement(signInOrCreateAccount);
			if (wait.getElementPresentState()) {
				if (getSignOrCreateAccountHeader().isDisplayed()) {
					getEmailInputBox().sendKeys("zxchbddfsdfsdfga@svdhdsfsdfssgsdj.com");
					clickOnContinueButton();
					getProceedToCreateAccountButton().click();
					logger.info("Setup correct registration page with default email.");
				} else {
					logger.error("Failed to setup registration page - Header not displayed.");
				}
			} else {
				logger.error("Failed to setup registration page - Element not visible.");
			}
		} catch (Exception e) {
			logger.error("Failed to setup registration page.", e);
			throw e;
		}
	}

	public boolean verifyPresenceOfElementsOnPage(List<String> fieldNames) {
		boolean result = true;
		int count = 0;
		for (String fieldName : fieldNames) {
			WebElement element = getElementByFieldName(fieldName);

			if (element == null) {
				System.out.println(
						"Element is null, so please make sure you have added corresponding WebElement for this field in excel: "
								+ fieldName);
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
			case "email inputbox":
				getEmailInputBox();
				element = emailInputBox;
				break;
			case "password inputbox":
				getPasswordInputBox();
				element = passwordInputBox;
				break;
			case "Continue Button":
				getPasswordInputBox();
				element = continueButton;
				break;
			default:
				throw new IllegalArgumentException("Invalid field name: " + fieldName);
			}
		} catch (IllegalArgumentException ie) {
			System.out.println(ie.getMessage());

		}
		return element;

	}

	public void setupCorrectSignInPage() {
		try {
			wait.waitForVisibilityOfElement(signInOrCreateAccount);
			if (wait.getElementPresentState()) {
				if (getSignOrCreateAccountHeader().isDisplayed()) {
					getEmailInputBox().sendKeys("test@test.com");
					getContinueButton().click();
					logger.info("Sign In page opened");
				} else {
					logger.error("Sign in or create account not shown, moving to next steps");
				}
			} else {
				logger.error("Moving to next steps on sign in page");
			}
		} catch (Exception e) {
			logger.error("Failed to setup sign in page.", e);
			throw e;
		}
	}

}
