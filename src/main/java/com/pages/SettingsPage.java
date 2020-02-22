/**
 * @author UmaMaheswararao
 */

package com.pages;

import static com.util.ActionUtil.getElement;

import org.openqa.selenium.By;
import org.testng.Assert;



public class SettingsPage {
	// Objects Repository(OR):........................................................................
	// Page verification
	static By setUpText = By.xpath("//span[contains(.,'Setup')]");
	
	
	

	// Actions:......................................................................................
	public static void verify_Navigate_to_Settings_page() {
		Assert.assertTrue(getElement("setUpText", setUpText).isDisplayed(), "Settings pagfe not opened");
	}
	
	
	
	
	
	

}
