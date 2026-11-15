package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.base.BasePage;

public class HomePage extends BasePage{	
		
	private Actions action;
	
	@FindBy(id = "nav-link-accountList")
	public WebElement accountsAndLists;
	
	@FindBy(xpath = "//div[@id='nav-flyout-ya-signin']//span[text()='Sign in']")
	public WebElement signInButton;
	
	@FindBy(xpath = "//div[@id='nav-flyout-accountList']//div[@id='nav-flyout-ya-signin']//div//a[text()='Start here.']")
	public WebElement newCustomerStartsHere;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		action = new Actions(driver);
	}
	
	//Open the Registration page
	public void openRegistrationPage() {
		action.moveToElement(getAccountAndListsElement()).build().perform();
		getStartsHereElement().click();
	}

	public void openSignInPage() {
		action.moveToElement(getAccountAndListsElement()).perform();
		getSignInButtonElement().click();
		
	}
	
	
	//Getter methods for the WebElements
	
	public WebElement getAccountAndListsElement() {
		wait.waitForVisibilityOfElement(accountsAndLists);
		return accountsAndLists;
		
	}
	
	public WebElement getStartsHereElement() {
		wait.waitForVisibilityOfElement(newCustomerStartsHere);
		return newCustomerStartsHere;
	}
	
	public WebElement getSignInButtonElement() {
		wait.waitForVisibilityOfElement(signInButton);
		return signInButton;
	}

}
