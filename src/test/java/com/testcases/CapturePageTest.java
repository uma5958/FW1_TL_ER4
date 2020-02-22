/**
 * @author UmaMaheswararao
 */

package com.testcases;

import static com.pages.CapturePage.verifyPageTitle;
import static com.pages.DashBoardPage.clickOnCaptureLink;
import static com.pages.LoginPage.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.util.ExcelUtility;

public class CapturePageTest extends BasePage {

	public ExcelUtility reader;

	@BeforeMethod
	public void setUp() throws Exception {
		start();
		launchBowser();
		login(prop.getProperty("username"), prop.getProperty("password"));
		clickOnCaptureLink();
		reader = new ExcelUtility(testDataFilePath);
	}



	@Test
	public void CapturePage_Title_Test() {
		String actual = verifyPageTitle();
		String expected = "Capture";
		Assert.assertEquals(actual, expected, "Capture Page Not Opened");
		Reporter.log("Capture Page Opened Successfully", true);
	}








}
