/**
 * @author UmaMaheswararao
 */

package com.testcases;

import static com.pages.DashBoardPage.verifyDashboardLink;
import static com.pages.DashBoardPage.verifyHomePageTitle;
import static com.pages.LoginPage.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.util.ExcelUtility;

public class DashBoardPageTest extends BasePage {

	public ExcelUtility reader;

	@BeforeMethod
	public void setUp() throws Exception {
		start();
		launchBowser();
		login(prop.getProperty("username"), prop.getProperty("password"));
		reader = new ExcelUtility(testDataFilePath);
	}

	

	@Test
	public void Verify_HomePage_Title_Test(){
		String dashBoardTitle = verifyHomePageTitle();
		Assert.assertTrue(dashBoardTitle.contains("Valuechain.com"),"Home page title not matched");
		Reporter.log("Title Verified - Test PASS", true);
	}

	@Test
	public void Verify_DashBoardLink_Test(){
		Assert.assertTrue(verifyDashboardLink(), "Dashboard Link Not Present - Test FAIL");
		Reporter.log("DashBoardLink Verified - Test PASS", true);
	}








}
