package com.pages;

import static com.util.ActionUtil.*;
import static com.base.BasePage.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.Reporter;


public class LoginPage {

	// Objects Repository(OR):........................................................................
	static By username = By.id("txtLoginName");
	static By password = By.name("password");
	static By loginBtn = By.id("btnLogin");
	static By menuBtn = By.xpath("(//i[contains(@style,'color: #FFFFFF;')])[1]");
	static By newiQaptureBtn = By.xpath("//span[text()='iQapture']");
	static By dashBoardLink = By.xpath("//span[text()='Dashboard']");
	static By valuechainLogo = By.xpath("//img[contains(@src,'ex.png')]");
	static By errorMsg = By.xpath(".//*[@id='info']/span");










	// Actions:......................................................................................
	public static  String validate_LoginPage_Title() throws Exception{
		createNode("Page title test");
		log("Title of the Login Page is: "+getDriver().getTitle());
		return getDriver().getTitle();
	}

	public static void login(String un, String pwd) throws Exception{
		createNode("Login to site with valid credentials");
		sendKeys("username", username, un);
		sendKeys("password", password, pwd);
		jsClick("loginBtn", loginBtn);
		jsClick("menuBtn", menuBtn);
		click("newiQaptureBtn", newiQaptureBtn);
		Assert.assertTrue(getWebElement("dashBoardLink", dashBoardLink).isDisplayed());
		log("Dashboard page displayed and login successful");
	}

	public static void verify_Valuechain_wesite_navigation_on_clicking_Valuechain_logo(String expTitle) throws Exception {
		click("valuechainLogo", valuechainLogo);
		switchToNewWindow();
		Thread.sleep(3000);
		String title = tldriver.get().getTitle();
		Reporter.log("Title of the page is: "+title, true);
		Assert.assertTrue(title.equalsIgnoreCase(expTitle), "Unable to navigate valuechain website");
	}

	public static void verify_Login_form(String un, String pwd) throws Exception {
		try {
			waitForElementVisibility("username", username);
			Assert.assertTrue(getWebElement("username", username).isDisplayed(), "Login NOT opened");
			Reporter.log("Login page opened successfully", true);
			sendKeys("username", username, un);
			sendKeys("password", password, pwd);
			jsClick("loginBtn", loginBtn);
			jsClick("menuBtn", menuBtn);
			click("newiQaptureBtn", newiQaptureBtn);
			Assert.assertTrue(getElement("dashBoardLink", dashBoardLink).isDisplayed(), "Login FAIL");
			Reporter.log("Login PASS & DashBoard page opened successfully");
		} catch (NoSuchElementException e) {
			String msg = getText("errorMsg", errorMsg);
			if (msg.equalsIgnoreCase("Please fill email.")) {
				Reporter.log("Invalid EMAIL", true);
				Assert.fail();
			} else if(msg.equalsIgnoreCase("Please fill password.")){
				Reporter.log("Invalid PASSWORD", true);
				Assert.fail();
			} else {
				Reporter.log("Sorry, the username or password is incorrect. Please try again.", true);
				Assert.fail();
			}
		}
	}




}



