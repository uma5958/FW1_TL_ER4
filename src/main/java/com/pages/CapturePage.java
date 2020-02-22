/**
 * @author UmaMaheswararao
 */

package com.pages;


import static com.util.ActionUtil.getWebElement;

import org.openqa.selenium.By;

public class CapturePage {

	// Objects Repository(OR):........................................................................
	//Page Title Verification
	static By captureText = By.xpath("//a[text()='Capture']");
	




	
	// Actions:......................................................................................
	public static String verifyPageTitle() {
		return getWebElement("captureText", captureText).getText();
	}












}
