/**
 * @author UmaMaheswararao
 */

package com.pages;

import static com.base.BasePage.getDriver;
import static com.util.ActionUtil.click;
import static com.util.ActionUtil.getWebElement;
import static com.util.ActionUtil.jsClick;

import org.openqa.selenium.By;

public class DashBoardPage {
	// Objects Repository(OR):........................................................................
	// Home Page Links
	static By dashBoardLink = By.xpath("//span[contains(text(),'Dashboard')]");
	static By buildLink = By.xpath("//span[contains(text(),'Build')]");
	static By captureLink = By.xpath("//span[contains(text(),'Capture')]");
	static By AnalyseLink = By.xpath("//span[contains(text(),'Analyse')]");
	static By SettingsLink = By.xpath("//span[contains(.,'Settings')]");

	
	
	// Actions:......................................................................................
	public static String verifyHomePageTitle(){
		return getDriver().getTitle();
	}

	// Navigating DashBoard Link
	public static boolean verifyDashboardLink(){
		return getWebElement("dashBoardLink", dashBoardLink).isDisplayed();
	}

	// Navigating Build Link
	public static void clickOnBuildLink() throws Exception {
		jsClick("buildLink", buildLink);
	}

	// Navigating Capture Link
	public static void clickOnCaptureLink() throws Exception {
		click("captureLink", captureLink);
	}

	// Navigating to Analyse Link
	public static void clickOnAnalyseLink() throws Exception {
		click("AnalyseLink", AnalyseLink);
	}
	
	// Navigating to Settings Link
	public static void clickOnSettingsLink() throws Exception {
		click("SettingsLink", SettingsLink);
	}


	
	
	

}
