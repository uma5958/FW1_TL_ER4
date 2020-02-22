/**
 * @author UmaMaheswararao
 */

package com.testcases;

import static com.pages.DashBoardPage.clickOnSettingsLink;
import static com.pages.LoginPage.login;
import static com.pages.SettingsPage.verify_Navigate_to_Settings_page;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.util.ExcelUtility;

public class SettingsPageTest extends BasePage {
	
	public ExcelUtility reader;
	
	@BeforeMethod
	public void setUp() throws Exception {
		start();
		launchBowser();
		login(prop.getProperty("username"), prop.getProperty("password"));
		clickOnSettingsLink();
		reader = new ExcelUtility(testDataFilePath);
	}
	
	

	@Test
	public void verify_Navigate_to_Settings_page_Test() {
		verify_Navigate_to_Settings_page();
		Reporter.log("Settings Page Opened Successfully", true);
	}

	
	
	
	
}
