/**

 * @author UmaMaheswararao
 */

package com.testcases;

import static com.pages.AnalysePage.verify_AnalysePage;
import static com.pages.DashBoardPage.clickOnAnalyseLink;
import static com.pages.LoginPage.login;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BasePage;
import com.util.ExcelUtility;

public class AnalysePageTest extends BasePage {
	
	public ExcelUtility reader;

	@BeforeMethod
	public void setUp() throws Exception {
		start();
		launchBowser();
		login(prop.getProperty("username"), prop.getProperty("password"));
		clickOnAnalyseLink();
		reader = new ExcelUtility(testDataFilePath);
	}

	
	
	
	@Test
	public void AnalysePage_Test() throws Exception {
		verify_AnalysePage();
		Reporter.log("Analyse Page Verified Successfully",true);
	}
	
	
	
	
	
	
	
	
}
