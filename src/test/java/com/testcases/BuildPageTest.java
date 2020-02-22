/**
 * @author UmaMaheswararao
 */

package com.testcases;

import static com.pages.BuildPage.verify_IQuapturePage;
import static com.pages.DashBoardPage.clickOnBuildLink;
import static com.pages.LoginPage.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.util.ExcelUtility;

public class BuildPageTest extends BasePage {
	
	public ExcelUtility reader;

	@BeforeMethod
	public void setUp() throws Exception {
		start();
		launchBowser();
		login(prop.getProperty("username"), prop.getProperty("password"));
		clickOnBuildLink();
		reader = new ExcelUtility(testDataFilePath);
	}


	
	@Test
	public void Verify_BuidPage_Test() {
		Assert.assertTrue(verify_IQuapturePage(), "New IQapture Button is Missing on the Page");
		Reporter.log("New IQapture Button Present - Test PASS", true);
	}

	
	
	
	
	
}
